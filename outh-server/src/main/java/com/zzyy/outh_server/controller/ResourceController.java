package com.zzyy.outh_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/user/{username}")
    public String user(@PathVariable String username) {
        return "zzyy" + username;
    }

}
