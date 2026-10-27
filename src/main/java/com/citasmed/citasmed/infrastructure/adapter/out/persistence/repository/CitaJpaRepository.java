package com.citasmed.citasmed.infrastructure.adapter.out.persistence.repository;

import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CitaJpaRepository extends JpaRepository<CitaEntity, UUID> {

    List<CitaEntity> findByMedicoId(UUID medicoId);
    List<CitaEntity> findByPacienteId(UUID pacienteId);
    List<CitaEntity> findByEstadoAndFechaHoraBetween(String estado, LocalDateTime desde, LocalDateTime hasta);
    List<CitaEntity> findByEstadoAndRecordatorioEnviadoFalseAndFechaHoraBetween(String estado, LocalDateTime desde, LocalDateTime hasta);

}