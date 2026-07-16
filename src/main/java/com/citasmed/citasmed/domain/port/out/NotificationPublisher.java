package com.citasmed.citasmed.domain.port.out;

import com.citasmed.citasmed.domain.model.Cita;

public interface NotificationPublisher {

    void publicarRecordatorio(Cita cita);
}
