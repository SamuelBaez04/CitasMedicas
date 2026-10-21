package com.citasmed.citasmed.infrastructure.adapter.in.web.dto;

import com.citasmed.citasmed.domain.model.Paciente;

import java.util.UUID;

public record PacienteResponse(UUID id, String nombre, String email, String telefono) {

    public static PacienteResponse desde(Paciente paciente){
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getTelefono()
        );
    }
}
