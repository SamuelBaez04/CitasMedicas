package com.citasmed.citasmed.domain.port.in;

import com.citasmed.citasmed.domain.model.Cita;

import java.util.UUID;

public interface GestionarCita {

    Cita confirmar(UUID citaId);

    Cita cancelar(UUID citaId);

    Cita completar(UUID citaId);

}
