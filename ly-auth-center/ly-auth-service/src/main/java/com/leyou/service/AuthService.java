package com.leyou.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.client.UserClient;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private  JwtProperties jwtProperties;

    public String accredit(String username, String password) throws Exception {

        try{
            User user = userClient.queryUser(username, password);
            if (null == user) {
                return null;
            }
            //产生token
        String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), jwtProperties.getPrivateKey(), jwtProperties.getExpire());

        return token;
    }catch (Exception e){
            return null;
        }
    }
}
