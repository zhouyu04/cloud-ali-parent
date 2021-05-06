package com.zzyy.outh_server.config;

import com.zzyy.outh_server.entity.UserInfo;
import com.zzyy.outh_server.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author zhouy262
 * @date 2021/5/6 10:05
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //获取本地用户
        UserInfo user = userMapper.selectByUserName(userName);
        if (user != null) {

            String role = StringUtils.isBlank(user.getRole()) ? "admin" : user.getRole();

            //返回oauth2的用户
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    passwordEncoder.encode(user.getPassword()),
                    AuthorityUtils.createAuthorityList(role));
        } else {
            throw new UsernameNotFoundException("用户[" + userName + "]不存在");
        }
    }
}
