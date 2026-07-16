package com.citasmed.citasmed.domain.port.in;

import com.citasmed.citasmed.domain.model.Cita;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AgendarCita {

    Cita agendar(UUID pacienteId, UUID medicoId, LocalDateTime fechaHora, String motivo);


}
