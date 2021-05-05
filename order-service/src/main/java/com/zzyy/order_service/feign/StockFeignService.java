package com.zzyy.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "stock-server")
public interface StockFeignService {

    @PostMapping("/stock")
    public String addOrder(@RequestParam("num") String num);

}
