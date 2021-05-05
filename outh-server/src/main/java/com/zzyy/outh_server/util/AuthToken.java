package com.zzyy.outh_server.util;

import lombok.Data;

@Data
public class AuthToken {

    //令牌信息
    String accessToken;
    //刷新token(refresh_token)
    String refreshToken;
    //jwt短令牌
    String jti;
}
