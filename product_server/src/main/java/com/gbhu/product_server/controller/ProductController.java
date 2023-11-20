package com.gbhu.product_server.controller;

import com.gbhu.product_server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/product")
public class ProductController {
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
    public Object findById(@RequestParam("id") int id) {
        return productService.findById(id);
    }

}
