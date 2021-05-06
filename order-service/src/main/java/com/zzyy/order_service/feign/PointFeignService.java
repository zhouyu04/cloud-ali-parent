package com.zzyy.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhouy262
 * @date 2021/5/6 15:13
 **/
@Component
@FeignClient(value = "point-server")
public interface PointFeignService {

    @PostMapping("/point")
    public String addOrder(@RequestParam("point") String point);
}
