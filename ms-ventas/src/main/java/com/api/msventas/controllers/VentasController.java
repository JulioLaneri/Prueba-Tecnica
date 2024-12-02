package com.api.msventas.controllers;

import com.api.msventas.dtos.ProductDto;
import com.api.msventas.interfaces.IVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VentasController {
    private final IVentaService ventaService;

    @Autowired
    public VentasController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping("/catalogo")
    List<ProductDto> getAll() {
        return ventaService.getAllProducts();
    }

    @GetMapping("/catalogo")
    ProductDto getById(@PathVariable String productId) {
        return ventaService.getProductById(productId);
    }



    @PostMapping("/ventas")
    String realizarVenta(@Valid @RequestBody ProductDto producto) {
        return ventaService.procesarVenta(producto);
    }
}
