package com.api.msventas.utils.rabbitmq;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Log4j2
public class VentasConfirmListener {

    @RabbitListener(queues = "ventas_confirm_queue")
    public void recibirConfirmacion(Map<String, Object> mensaje) {
        log.info("Venta confirmada: {}", mensaje);
    }
}
