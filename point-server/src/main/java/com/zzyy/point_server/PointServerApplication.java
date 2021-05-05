package com.zzyy.point_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PointServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PointServerApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    class EchoController {

        @Autowired
        RestTemplate restTemplate;


        @PostMapping("/point")
        public String addOrder(@RequestParam String point) {


            System.out.println("增加积分:" + point);

            return "success";


        }

    }
}
