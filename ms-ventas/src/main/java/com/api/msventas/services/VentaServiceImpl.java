package com.api.msventas.services;

import com.api.msventas.dtos.ProductDto;
import com.api.msventas.exceptions.BadRequestException;
import com.api.msventas.interfaces.IVentaService;
import com.api.msventas.utils.rabbitmq.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class VentaServiceImpl implements IVentaService {
    private final RabbitMqSender rabbitMqSender;
    private final RestTemplate restTemplate;

    @Value("${ms-stock.base-url}")
    private String msStockBaseUrl;

    @Autowired
    public VentaServiceImpl(RabbitMqSender rabbitMqSender, RestTemplate restTemplate) {
        this.rabbitMqSender = rabbitMqSender;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(msStockBaseUrl, ProductDto[].class);
        ProductDto[] productsArray = response.getBody();
        return productsArray != null ? Arrays.asList(productsArray) : Collections.emptyList();
    }


    @Override
    public ProductDto getProductById(String id) {
        return restTemplate.getForEntity(msStockBaseUrl + "/" + id, ProductDto.class).getBody();
    }

    @Override
    public String procesarVenta(ProductDto producto) {
        ProductDto product = restTemplate.getForEntity(msStockBaseUrl + "/" + producto.getProductId(), ProductDto.class).getBody();
        if (product == null || product.getQuantity() < producto.getQuantity() ) {
            throw new BadRequestException("El producto no se puede ser negativo");
        }
        rabbitMqSender.enviarVenta(product);
        return "Se realizó la venta";
    }
}
