package com.zzyy.outh_server.service;

import com.zzyy.outh_server.util.AuthToken;

public interface AuthService {
    AuthToken login(String username, String password, String clientId, String clientSecret);

    String findByUsername(String username);
}
