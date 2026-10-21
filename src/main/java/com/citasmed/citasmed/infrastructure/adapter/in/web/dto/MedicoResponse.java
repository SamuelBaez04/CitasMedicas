package com.citasmed.citasmed.infrastructure.adapter.in.web.dto;

import com.citasmed.citasmed.domain.model.Medico;

import java.util.UUID;

public record MedicoResponse(UUID id, String nombre, String especialidad, String email) {

    public static MedicoResponse desde(Medico medico){
        return new MedicoResponse(
                medico.getId(),
                medico.getNombre(),
                medico.getEspecialidad(),
                medico.getEmail()
        );
    }
}
