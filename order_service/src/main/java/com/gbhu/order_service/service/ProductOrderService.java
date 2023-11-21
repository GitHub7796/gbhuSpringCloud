package com.gbhu.order_service.service;

import com.gbhu.order_service.domain.ProductOrder;

/**
 * 订单业务类
 */
public interface ProductOrderService {
    /**
     * 下单
     * @param userId
     * @param productId
     * @return
     */
    ProductOrder save(int userId, int productId);

    /**
     * 下单
     * @param userId
     * @param productId
     * @return
     */
    ProductOrder saveByFeign(int userId, int productId);
}
