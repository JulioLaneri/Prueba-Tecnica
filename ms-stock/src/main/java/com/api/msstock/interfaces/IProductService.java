package com.api.msstock.interfaces;

import com.api.msstock.dtos.ProductDto;

public interface IProductService {
    ProductDto createProduct(ProductDto product);
    ProductDto getProduct(String productId);
}
