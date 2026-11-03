package com.citasmed.citasmed.infrastructure.adapter.out.notification;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.port.out.NotificationPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitNotificationPublisher implements NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String cola;

    public RabbitNotificationPublisher(RabbitTemplate rabbitTemplate, @Value("{citasmed.recordatorios.cola}") String cola){
        this.rabbitTemplate = rabbitTemplate;
        this.cola = cola;
    }

    @Override
    public void publicarRecordatorio(Cita cita){
        rabbitTemplate.convertAndSend(cola, cita.getId().toString());
    }


}
