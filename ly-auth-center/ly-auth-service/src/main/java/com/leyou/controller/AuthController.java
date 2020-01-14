package com.leyou.controller;


import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.client.UserClient;
import com.leyou.config.JwtProperties;
import com.leyou.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private  JwtProperties jwtProperties;

    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username") String username, @RequestParam("password") String password,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        //产生token加密字符串
        String token=authService.accredit(username,password);
        if(null==token){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //向浏览器端写入cookie
        CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getCookieMaxAge(),null,true);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("verify")
    public  ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN") String s,HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserInfo infoFromToken = JwtUtils.getInfoFromToken(s, jwtProperties.getPublicKey());
        if(infoFromToken!=null){

            //刷新Cookie里面的字符串
            //产生token
            String token=JwtUtils.generateToken(infoFromToken,jwtProperties.getPrivateKey(),jwtProperties.getExpire());
            //向浏览器写入cookie
            CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getCookieMaxAge());

            return  ResponseEntity.ok(infoFromToken);
        }

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
