package com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.CitaEntity;

public class CitaMapper {

    public static CitaEntity aEntity(Cita cita) {
        return new CitaEntity(
                cita.getId(),
                PacienteMapper.aEntity(cita.getPaciente()),
                MedicoMapper.aEntity(cita.getMedico()),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getEstado().name(),
                cita.isRecordatorioEnviado()
        );
    }

    public static Cita aDominio(CitaEntity entity) {
        return Cita.reconstruir(
                entity.getId(),
                PacienteMapper.aDominio(entity.getPaciente()),
                MedicoMapper.aDominio(entity.getMedico()),
                entity.getFechaHora(),
                entity.getMotivo(),
                EstadoCita.valueOf(entity.getEstado()),
                entity.isRecordatorioEnviado()
        );
    }

}