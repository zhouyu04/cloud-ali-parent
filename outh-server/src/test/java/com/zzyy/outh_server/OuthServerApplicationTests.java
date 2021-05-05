package com.zzyy.outh_server;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

@SpringBootTest
class OuthServerApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void testCreateToken() {

        ClassPathResource resource = new ClassPathResource("zzyy.jks");

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "zzyy0320".toCharArray());

        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("zzyy", "zzyy0320".toCharArray());

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        JSONObject params = new JSONObject();
        params.put("name","zzyy");
        params.put("age","18");
        params.put("address","sz");

        Jwt jwt = JwtHelper.encode(params.toJSONString(), new RsaSigner(privateKey));

        String encoded = jwt.getEncoded();
        System.out.println(encoded);


    }

    @Test
    public void testParseToken(){

        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoic3oiLCJuYW1lIjoienp5eSIsImFnZSI6IjE4In0.WXUPZWRT7CS2Rlkj_wUZTmr_7ls7twJBA3MJzHtZwoT0QoQVEvL7iIKXEkgzZPCF5Zd3clNvkxVkH8L6IsU2b3kJR0stE6uNkgHb6reNb2_ySPpmRNCsTX_mgEMBZdwthYnF-fETcxrpcbaUtHM6uWqUKpXQGaStdMvFK5TYUZbCsSIM5ExKokcMKSuRiIZVKcMyh12nnf5SOE3sxQZbiiZKgIGHxzFYBCRBpeJ9prTwtGOrkridSucsaX1ENj0_2YjvNkn7a5UYK-2aLNVEMlqD17tRWwRXbzNtvMTT4H4Eq8Q3yB7jo0LSRo9MQsQsWBgNWFZNE0uqSgDTPWKjuw";

        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhs0FtrcthUEmSlVo7yBPmHVJlonhTkrXQZipxNoJmFH79GVmUVCSPiHoj15Qd5rBL1xp7AfsM5kEqX4c3yHzyg3ackPv6g+oq0DS6mj4Lz5m7m/0mAMOFXXfPc6s1yUFhXpLShLu7ztYPuqWCkSEK2LcALCM1M3nzxgbMIbRhg+CP6WkF/8edG0k2Eg6XQCVXmZcRMmlu/J2DeOn4laPH8p9THaAl60ov7CbaN5IxRu/sVfLQ/URD2/Iyq6BDz985RLieX28k+lwByaGxQlHKA+wkQdogiJO+eZRRM/scrbbU4O8O/6qbaiq24S/e/FfpcUsweoBSJPdGxqzMifbpwIDAQAB-----END PUBLIC KEY-----";

        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));

        String claims = jwt.getClaims();
        System.out.println(claims);


    }

}
