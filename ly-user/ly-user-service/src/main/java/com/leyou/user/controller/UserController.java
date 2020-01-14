package com.leyou.user.controller;

import com.leyou.pojo.User;
import com.leyou.user.service.UserService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("data") String data,@PathVariable("type") Integer type){
            Boolean bol=userService.check(data,type);
            if(false==bol){
                return  ResponseEntity.status(400).build();
            }
            return  ResponseEntity.ok(bol);
    }

    @PostMapping("code")
    public ResponseEntity<Void>  sendVerifyCode(@RequestParam("phone") String phone){
         Boolean b= userService.sendVerifyCode(phone);
         if(null!=b&&b){
             return  ResponseEntity.ok().build();
         }
         return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public  ResponseEntity<Void> createUser(@Valid User user, @RequestParam("code") String code){
            Boolean bool=this.userService.register(user,code);
            if(bool==null || !bool){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据用户名和密码查询用户
     */
    @GetMapping("query")
    public  ResponseEntity<User> queryUser(@RequestParam("username") String username,@RequestParam("password") String password){
        User user=this.userService.queryUser(username,password);
        if(user!=null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
