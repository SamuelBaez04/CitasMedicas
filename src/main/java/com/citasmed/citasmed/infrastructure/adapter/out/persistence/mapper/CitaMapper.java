package com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.CitaEntity;

import java.lang.reflect.Field;

public class CitaMapper {

    public static CitaEntity aEntity(Cita cita) {
        return new CitaEntity(
                cita.getId(),
                PacienteMapper.aEntity(cita.getPaciente()),
                MedicoMapper.aEntity(cita.getMedico()),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getEstado().name()
        );
    }

    public static Cita aDominio(CitaEntity entity) {
        Cita cita = new Cita(
                entity.getId(),
                PacienteMapper.aDominio(entity.getPaciente()),
                MedicoMapper.aDominio(entity.getMedico()),
                entity.getFechaHora(),
                entity.getMotivo()
        );
        restaurarEstado(cita, EstadoCita.valueOf(entity.getEstado()));
        return cita;
    }


    private static void restaurarEstado(Cita cita, EstadoCita estado) {
        try {
            Field campoEstado = Cita.class.getDeclaredField("estado");
            campoEstado.setAccessible(true);
            campoEstado.set(cita, estado);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("No se pudo restaurar el estado de la cita", e);
        }
    }

}
