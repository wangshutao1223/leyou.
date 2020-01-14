package com.leyou.test;


import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class TestDemo {

    //先创建tmp/rsa目录
    private  static final String pubKeyPath="D:\\tmp\\rsa.pub";

    private static  final  String priKeyPath="D:\\tmp\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 产生公钥/私钥
     */
/*    @Test
    public   void init() throws Exception {
        RsaUtils.generateKey(pubKeyPath,priKeyPath,"ty56787");
    }*/
/**
 * 获取公钥/私钥
 */

    @Before
    public  void  loadData() throws Exception {
        //获取公钥
        publicKey= RsaUtils.getPublicKey(pubKeyPath);
        //获取私钥
        privateKey= RsaUtils.getPrivateKey(priKeyPath);
    }
    //产生token
   @Test
    public  void  getToken() throws Exception {
        String token = JwtUtils.generateToken(new UserInfo(12L, "tom"), privateKey, 5);
        System.out.println(token);
        //eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTIsInVzZXJuYW1lIjoidG9tIiwiZXhwIjoxNTc4NzI2OTAzfQ.Ahvmj6tMA32ozkIxZKEBtyMl640d5Dq90ax1fVLte2tCYeZoCAvfrXxTj1Naneci7u_XCX7K2kBnLc1VmpMO-2N2eMs0IZ3whn4C7UrZpGsQMdOZvpP6GybNt-BTT-izIyjvJo6AHnwSJ_vS4lFk-GgxGx46dBc3X0aWqBRetZ4
    }


    /**
     * 解密
     * @throws Exception
     */

    @Test
    public  void jiemi() throws Exception {
        String s="eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTIsInVzZXJuYW1lIjoidG9tIiwiZXhwIjoxNTc4NzI2OTAzfQ.Ahvmj6tMA32ozkIxZKEBtyMl640d5Dq90ax1fVLte2tCYeZoCAvfrXxTj1Naneci7u_XCX7K2kBnLc1VmpMO-2N2eMs0IZ3whn4C7UrZpGsQMdOZvpP6GybNt-BTT-izIyjvJo6AHnwSJ_vS4lFk-GgxGx46dBc3X0aWqBRetZ4";
        UserInfo userInfo = JwtUtils.getInfoFromToken(s, publicKey);
        System.out.println(userInfo);
    }
}
