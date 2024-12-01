package com.api.msstock.utils.rabbitmq;

import com.api.msstock.dtos.ProductDto;
import com.api.msstock.interfaces.IProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitMqListener {
    private final IProductService productService;

    @Autowired
    public RabbitMqListener(IProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "ventas_queue")
    public void procesarVenta(ProductDto product) {
        productService.updateStock(product.getProductId(), product.getQuantity());
    }
}
