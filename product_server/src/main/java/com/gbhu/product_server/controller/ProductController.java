package com.gbhu.product_server.controller;

import com.gbhu.product_server.domain.Product;
import com.gbhu.product_server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品列表
     * @return
     */
    @RequestMapping("list")
    public Object list() {
      return   productService.listProduct();
    }

    /**
     * 根据 id 查找商品
     * @param id
     * @return
     */
    @RequestMapping("find")
    public Object findById(@RequestParam("id") int id) {
        Product product=productService.findById(id);
        product.setName(product.getName()+"data from port="+port);
        return product;
    }

}
