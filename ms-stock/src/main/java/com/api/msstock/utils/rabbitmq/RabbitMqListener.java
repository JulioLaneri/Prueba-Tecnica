package com.api.msstock.utils.rabbitmq;

import com.api.msstock.dtos.ProductDto;
import com.api.msstock.interfaces.IProductService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component

public class RabbitMqListener {
    private final IProductService productService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqListener(IProductService productService, RabbitTemplate rabbitTemplate) {
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "ventas_queue")
    public void procesarVenta(ProductDto product) {
        productService.updateStock(product);
        Map<String, Object> mensajeConfirmacion = Map.of(
                "productId", product.getProductId(),
                "quantity", product.getQuantity(),
                "status", "success"
        );
        rabbitTemplate.convertAndSend("ventas_confirm_queue", mensajeConfirmacion);
    }

}
