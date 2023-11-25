package com.gbhu.order_service.controller;

import com.gbhu.order_service.service.ProductOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/order")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
//    @HystrixCommand(fallbackMethod = "saveOrderFail",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "100")
//    })
    public Object save(@RequestParam("user_id") int userId,
                       @RequestParam("product_id") int productId,
                       HttpServletRequest request) {
//        return productOrderService.save(userId, productId);
//        return productOrderService.saveByFeign(userId, productId);
        Map<String, Object> msg = new HashMap<>();
        msg.put("code", 0);
        msg.put("msg", productOrderService.saveByFeign(userId, productId));
        return msg;
    }

    private Object saveOrderFail(int userId, int productId,HttpServletRequest request) {

//        监控报警
        String saveOrderKey = "save-order";
        String sendValue = redisTemplate.opsForValue().get(saveOrderKey);
        final String ip = request.getRemoteAddr();
        new Thread(
                //程序立即反馈，开启个线程同步执行剩余内容1
                () -> {
                    if (StringUtils.isBlank(sendValue)) {
                        System.out.println("紧急短信，用户服务下单接口熔断，ip=" + ip);
                        redisTemplate.opsForValue().set(saveOrderKey, "save-order-fail", 20, TimeUnit.SECONDS);
                    } else {
                        System.out.println("已经发送过短信,20s后重发");

                    }
                }
        ).start();


        Map<String, Object> msg = new HashMap<>();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，被挤出来");
        return msg;
    }


}
