package com.zzyy.outh_server.service;

import com.zzyy.outh_server.util.AuthToken;

public interface LoginService {
    AuthToken login(String username, String password, String clientId, String clientSecret, String grandType);
}
