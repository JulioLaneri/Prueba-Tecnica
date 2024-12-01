package com.api.msstock.utils.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue ventasQueue() {
        return new Queue("ventas_queue", true);
    }
}
