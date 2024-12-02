package com.api.msstock.utils;

import com.api.msstock.dtos.ProductDto;
import com.api.msstock.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IProductService productService;

    @Autowired
    public DataInitializer(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) {

        ProductDto product1 = new ProductDto();
        product1.setProductId("123");
        product1.setProductName("Producto A");
        product1.setQuantity(100);

        ProductDto product2 = new ProductDto();
        product2.setProductId("124");
        product2.setProductName("Producto B");
        product2.setQuantity(50);

        // Guardar los productos
        productService.createProduct(product1);
        productService.createProduct(product2);

        System.out.println("Productos iniciales cargados exitosamente.");
    }
}