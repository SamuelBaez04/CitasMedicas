package com.citasmed.citasmed.domain.port.in;

import com.citasmed.citasmed.domain.model.Cita;

import java.util.List;
import java.util.UUID;

public interface ConsultarCita {

    List<Cita> porPaciente(UUID pacienteId);

    List<Cita> porMedico(UUID medicoId);

}
