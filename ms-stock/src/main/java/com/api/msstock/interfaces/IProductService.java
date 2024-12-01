package com.api.msstock.interfaces;

import com.api.msstock.dtos.ProductDto;

import java.util.List;

public interface IProductService {
    ProductDto createProduct(ProductDto product);
    ProductDto getProduct(String productId);
    List<ProductDto> getAllProducts();
    ProductDto updateStock(String productId, Integer quantity);
}
