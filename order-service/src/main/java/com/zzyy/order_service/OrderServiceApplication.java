package com.zzyy.order_service;

import com.zzyy.order_service.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @RestController
    class EchoController {

        @Autowired
        StockFeignService stockFeignService;

        @Autowired
        RestTemplate restTemplate;

        @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            return string;
        }


        @PostMapping("/addOrder")
        public String addOrder(@RequestParam String num) {

            System.out.println("开始订单：" + num);


//            String s = restTemplate.postForObject("http://127.0.0.1:9002/stock?num=" + num, num, String.class);

            String s = stockFeignService.addOrder(num);
            System.out.println("调用库存返回:" + s);

            String s1 = restTemplate.postForObject("http://127.0.0.1:9003/point?point=" + num, num, String.class);
            System.out.println("调用积分返回：" + s1);

            return "success";


        }

    }


}
