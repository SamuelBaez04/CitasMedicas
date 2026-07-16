package com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper;

import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.PacienteEntity;

public class PacienteMapper {

    public static PacienteEntity aEntity(Paciente paciente) {
        return new PacienteEntity(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getTelefono()
        );
    }

    public static Paciente aDominio(PacienteEntity entity) {
        return new Paciente(
                entity.getId(),
                entity.getNombre(),
                entity.getEmail(),
                entity.getTelefono()
        );
    }

}
