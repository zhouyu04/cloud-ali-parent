//package com.zzyy.gateway_server.config;
//
//import com.alibaba.cloud.commons.lang.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.lang.annotation.Annotation;
//import java.time.LocalDateTime;
//
///**
// * @author zhouy262
// * @date 2021/5/6 13:34
// **/
//@Component
//@Slf4j
//public class MyGlobalGatewayFilter implements GlobalFilter, Order {
//    @Override
//    public Class<? extends Annotation> annotationType() {
//        return null;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info("-----welcome to MyGlobalGatewatFilter------" + LocalDateTime.now().toString());
//        String password = exchange.getRequest().getQueryParams().getFirst("password");
////        Assert.notNull(password, "password must not be null ! ");
//        if (StringUtils.isBlank(password)) {
//            throw new RuntimeException("password must not be null !");
//        }
//        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (StringUtils.isEmpty(authorization)) {
//            log.info("Authorization is empty!!!");
//            ServerHttpResponse response = exchange.getResponse();
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int value() {
//        return 0;
//    }
//}
