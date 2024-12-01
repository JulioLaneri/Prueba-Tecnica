package com.api.msventas.controllers;

import com.api.msventas.dtos.ProductDto;
import com.api.msventas.interfaces.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogo")
public class VentasController {
    private final IVentaService ventaService;

    @Autowired
    public VentasController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping("/{productId}")
    ProductDto getById(@PathVariable String productId) {
        return ventaService.getProductById(productId);
    }

    @PostMapping
    String realizarVenta(@RequestBody ProductDto producto) {
        return ventaService.procesarVenta(producto);
    }
}
