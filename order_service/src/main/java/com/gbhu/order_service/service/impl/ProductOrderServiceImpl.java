package com.gbhu.order_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.gbhu.order_service.domain.ProductOrder;
import com.gbhu.order_service.service.ProductClient;
import com.gbhu.order_service.service.ProductOrderService;
import com.gbhu.order_service.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Override
    public ProductOrder save(int userId, int productId) {
        //调动商品服务获取商品详情
        Map<String, Object> productMap=restTemplate.getForObject(
                "http://product-service/api/v1/product/find?id="+productId
                , Map.class);
        //生成订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(productMap.get("name").toString());
        productOrder.setPrice(Integer.parseInt(productMap.get("price").toString()));
        return productOrder;
    }

    @Autowired
    private ProductClient productClient;

    @Override
    public ProductOrder saveByFeign(int userId, int productId) {
        //调动商品服务获取商品详情
        String json = productClient.findById(productId);
        //转化 json 字符串
        JsonNode jsonNode = JsonUtils.str2JsonNode(json);
        //生成订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductName(jsonNode.get("name").toString());
        productOrder.setPrice(Integer.parseInt(jsonNode.get("price").toString()));
        return productOrder;
    }
}
