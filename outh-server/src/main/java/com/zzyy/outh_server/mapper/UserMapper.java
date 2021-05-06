package com.zzyy.outh_server.mapper;

import com.zzyy.outh_server.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserInfo selectByUserName(String userName);
}
