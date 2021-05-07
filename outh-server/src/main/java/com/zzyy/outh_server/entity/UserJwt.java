package com.zzyy.outh_server.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class UserJwt extends User {
    private String id;    //用户ID
    private String name;  //用户名字
    private String role;

    private String comny;//设置公司

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getComny() {
        return comny;
    }

    public void setComny(String comny) {
        this.comny = comny;
    }
}
