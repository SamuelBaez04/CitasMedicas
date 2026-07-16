package com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper;

import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.MedicoEntity;

public class MedicoMapper {

    public static MedicoEntity aEntity(Medico medico) {
        return new MedicoEntity(
                medico.getId(),
                medico.getNombre(),
                medico.getEspecialidad(),
                medico.getEmail()
        );
    }

    public static Medico aDominio(MedicoEntity entity) {
        return new Medico(
                entity.getId(),
                entity.getNombre(),
                entity.getEspecialidad(),
                entity.getEmail()
        );
    }

}
