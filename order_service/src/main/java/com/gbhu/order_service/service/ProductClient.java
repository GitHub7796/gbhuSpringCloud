package com.gbhu.order_service.service;

import com.gbhu.order_service.fallback.ProductClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "product-service",fallback = ProductClientCallBack.class)
@FeignClient(name = "product-service",fallback = ProductClientFallback.class)
public interface ProductClient {
    @GetMapping("/api/v1/product/find")
    String findById(@RequestParam("id") int id);
}
