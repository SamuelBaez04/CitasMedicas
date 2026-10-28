package com.citasmed.citasmed.infrastructure.adapter.out.notification;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.port.out.NotificationPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogNotificationPublisher implements NotificationPublisher {

    private static final Logger log = LoggerFactory.getLogger(LogNotificationPublisher.class);

    @Override
    public void publicarRecordatorio(Cita cita){
        log.info("RECORDATORIO -> {} ({}): cita el {} con {}",
                cita.getPaciente().getNombre(), cita.getPaciente().getEmail(),
                cita.getFechaHora(), cita.getMedico().getNombre());
    }


}
