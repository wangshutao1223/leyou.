package com.leyou.config;

import com.leyou.auth.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@ConfigurationProperties(prefix ="ly.jwt" )
public class JwtProperties {




    private  String cookieName;


    private  String pubKeyPath;
    private  PublicKey publicKey;


    @PostConstruct//构造方法
    public  void init() throws Exception {

        //获取公钥
       publicKey = RsaUtils.getPublicKey(pubKeyPath);

    }



    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }


}
