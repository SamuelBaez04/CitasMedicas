package com.citasmed.citasmed.infrastructure;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.infrastructure.adapter.out.notification.RabbitNotificationPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RabbitNotificationPublisherTest {

    @Mock private RabbitTemplate rabbitTemplate;

    @Test
    void publicarElIdDeLaCitaEnLaCola(){
        Paciente paciente = new Paciente(UUID.randomUUID(), "Samuel", "samuel@email.com", "3122169275");
        Medico medico = new Medico(UUID.randomUUID(), "Dra.Agudelo", "Medicina General", "agudelo@email.com");
        Cita cita = new Cita(UUID.randomUUID(), paciente, medico, LocalDateTime.now().plusHours(5), "Control");
        RabbitNotificationPublisher publisher = new RabbitNotificationPublisher(rabbitTemplate,"recordatorios.citas");
        publisher.publicarRecordatorio(cita);
        verify(rabbitTemplate).convertAndSend("recordatorios.citas", cita.getId().toString());
    }
}
