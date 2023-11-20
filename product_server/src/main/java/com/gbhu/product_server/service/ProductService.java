package com.gbhu.product_server.service;

import com.gbhu.product_server.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> listProduct();

    Product findById(int id);
}
