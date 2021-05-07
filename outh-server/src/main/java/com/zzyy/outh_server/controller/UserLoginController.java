package com.zzyy.outh_server.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zzyy.outh_server.entity.Result;
import com.zzyy.outh_server.entity.StatusCode;
import com.zzyy.outh_server.service.LoginService;
import com.zzyy.outh_server.util.AuthToken;
import com.zzyy.outh_server.util.CookieUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouy262
 * @date 2021/5/6 17:26
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private LoginService loginService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    private static final String GRAND_TYPE = "password";// 授权模式 密码模式

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    // Cookie生命周期
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    /**
     * 密码模式 认证.
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public Result<Map> login(String username, String password) {
        //登录 之后生成令牌的数据返回
        AuthToken authToken = loginService.login(username, password, clientId, clientSecret, GRAND_TYPE);


        //设置到cookie中
        saveCookie(authToken.getAccessToken());
        return new Result<>(true, StatusCode.OK, "令牌生成成功", authToken);
    }

    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", "Authorization", token, cookieMaxAge, false);
    }

}
