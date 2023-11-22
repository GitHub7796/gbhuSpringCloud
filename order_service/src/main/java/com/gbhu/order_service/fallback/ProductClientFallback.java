package com.gbhu.order_service.fallback;

import com.gbhu.order_service.service.ProductClient;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public String findById(int id) {
        System.out.println("降级处理");
        return null;
    }
}
