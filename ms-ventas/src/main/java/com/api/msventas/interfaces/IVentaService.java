package com.api.msventas.interfaces;

import com.api.msventas.dtos.ProductDto;

import java.util.List;

public interface IVentaService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(String id);
    String procesarVenta(ProductDto producto);
}
