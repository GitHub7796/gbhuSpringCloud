package com.gbhu.order_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-service")
public interface ProductClient {
    @GetMapping("/api/v1/product/find")
    String findById(@RequestParam("id") int id);
}
