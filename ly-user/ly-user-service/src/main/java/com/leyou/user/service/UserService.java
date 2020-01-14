package com.leyou.user.service;

import com.leyou.pojo.User;
import com.leyou.user.mapper.UserMapper;
import com.leyou.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import utils.NumberUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //标识
    static  final  String KEY_PREFIX="user:code:phone";

    public Boolean check(String data, Integer type) {

        //1、用户名 2、手机
        User user = new User();



        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
        }
        //0 / 1以上的
        return  userMapper.selectCount(user)!=1; //0!=1 true
    }

    public Boolean sendVerifyCode(String phone) {
        //产生验证码
        String s = NumberUtils.generateCode(5);//随机五位数

        //吧验证码放到redis
        stringRedisTemplate.opsForValue().set(KEY_PREFIX+phone,s,5, TimeUnit.MINUTES);
        //user:code:phone

        //发短信(略)
        //调用第三方接口
        return true;
    }

    public Boolean register(User user, String code) {
        //校验验证码
        String s = this.stringRedisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());

        //判断
        if(null==s){
            return  false;
        }
        //取到数据,填错了
        if(!code.equals(s)){
            return false;
        }

        //插入数据
        //把用户名拿到数据库查询，返回，实现


        //
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        //加密
        String newPassword = CodecUtils.md5Hex(user.getPassword(), salt);
        //设置加密密码
        user.setPassword(newPassword);

        user.setCreated(new Date());

        boolean flag = this.userMapper.insert(user) == 1;
        if(flag){
            //redis数据删掉
            this.stringRedisTemplate.delete(KEY_PREFIX+user.getPhone());
        }
        return flag;
    }

    public User queryUser(String username, String password) {
        //查询用户
        User user = new User();
        user.setUsername(username);
        User user1 = this.userMapper.selectOne(user);
        //用户为空
        if(null==user1){
            return  null;
        }

        //取出盐
        String salt = user1.getSalt();

        //加密密码

        String newPassword = CodecUtils.md5Hex(password, salt);

        //判断加密后与数据库是否一样
        if(!user1.getPassword().equals(newPassword)){
                return  null;
        }
        return user1;
    }
}
