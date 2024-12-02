package com.api.msventas.utils.rabbitmq;

import com.api.msventas.dtos.ProductDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitMqSender {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarVenta(ProductDto product) {
        rabbitTemplate.convertAndSend("ventas_queue", product);
    }
}

