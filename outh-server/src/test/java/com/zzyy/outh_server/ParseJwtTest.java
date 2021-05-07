package com.zzyy.outh_server;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:48
 * @Description: com.changgou.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UxIl0sInNjb3BlIjpbImFwcCJdLCJuYW1lIjpudWxsLCJpZCI6bnVsbCwiZXhwIjoxNjIwMzYxNzc1LCJjb21weSI6InNvbmdzaSIsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6IjUyMTBmZjdlLTMzMzAtNGQyNi1iOWQyLTVlMzRjOTc2OWMzNiIsImNsaWVudF9pZCI6ImNsaWVudC1hIiwidXNlcm5hbWUiOiJ6eSJ9.MwTVgkwDMhPEOZU6_C3yATFxsYOsHAuOtZ6MJyTm6ivQ0i5dgzna7qBIOozlbxosTB8Uex3m8hwOIAQ6_lGUnuTwB2_MR-6iwxfRmkXUaLL7qZLbdwrhcKv6ILVe2LxJCMDMGMw4zDdCmbvNYceuzS5gLm3Qcrmrw715J-lJndkmTmHoXKmqmigVcosXRSaJQ8EFF5e4yKXfeJ7yO-O03vZ4rWgli8Q25GLEaAFWkbSxWp7Y8ANyWHfn-WnKwC8m5EGIRlypNJIk_agrnWGx10qME6dpG7Lsj4Kck4WlDsBl7dgOmGl-BDvT1SyZ8oVA92I1KSNSxDF2x9JLvWjfGg";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhs0FtrcthUEmSlVo7yBPmHVJlonhTkrXQZipxNoJmFH79GVmUVCSPiHoj15Qd5rBL1xp7AfsM5kEqX4c3yHzyg3ackPv6g+oq0DS6mj4Lz5m7m/0mAMOFXXfPc6s1yUFhXpLShLu7ztYPuqWCkSEK2LcALCM1M3nzxgbMIbRhg+CP6WkF/8edG0k2Eg6XQCVXmZcRMmlu/J2DeOn4laPH8p9THaAl60ov7CbaN5IxRu/sVfLQ/URD2/Iyq6BDz985RLieX28k+lwByaGxQlHKA+wkQdogiJO+eZRRM/scrbbU4O8O/6qbaiq24S/e/FfpcUsweoBSJPdGxqzMifbpwIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
