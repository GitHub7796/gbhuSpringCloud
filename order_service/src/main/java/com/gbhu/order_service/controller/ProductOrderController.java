package com.gbhu.order_service.controller;

import com.gbhu.order_service.service.ProductOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/order")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id") int userId,
                       @RequestParam("product_id") int productId) {
//        return productOrderService.save(userId, productId);
//        return productOrderService.saveByFeign(userId, productId);
        Map<String, Object> msg = new HashMap<>();
        msg.put("code", 0);
        msg.put("msg", productOrderService.saveByFeign(userId, productId));
        return msg;
    }

    private Object saveOrderFail(int userId, int productId) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，被挤出来");
        return msg;
    }


}
