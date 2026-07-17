package com.citasmed.citasmed.infrastructure.adapter.in.web.dto;

import com.citasmed.citasmed.domain.model.Cita;

import java.time.LocalDateTime;
import java.util.UUID;

public record CitaResponse(
        UUID id,
        UUID idPaciente,
        String pacienteNombre,
        UUID idMedico,
        String medicoNombre,
        LocalDateTime fechaHora,
        String motivo,
        String estado
) {

    public static CitaResponse desde(Cita cita){
        return new CitaResponse(
                cita.getId(),
                cita.getPaciente().getId(),
                cita.getPaciente().getNombre(),
                cita.getMedico().getId(),
                cita.getMedico().getNombre(),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getEstado().name()
        );
    }

}
