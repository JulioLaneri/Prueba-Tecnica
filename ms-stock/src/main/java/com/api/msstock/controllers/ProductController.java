package com.api.msstock.controllers;

import com.api.msstock.dtos.ProductDto;
import com.api.msstock.interfaces.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto create(@Valid @RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    ProductDto getById(@PathVariable String productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ProductDto> getAll() { return productService.getAllProducts(); }

}
