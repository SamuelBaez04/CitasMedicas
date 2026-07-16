package com.citasmed.citasmed.domain.exception;

import java.time.LocalDateTime;
import java.util.UUID;

public class MedicoNoDisponibleException extends RuntimeException{

    public MedicoNoDisponibleException(UUID medicoId, LocalDateTime fechaHora) {
        super("El médico " + medicoId + " ya tiene una cita agendada en: " + fechaHora);
    }

}
