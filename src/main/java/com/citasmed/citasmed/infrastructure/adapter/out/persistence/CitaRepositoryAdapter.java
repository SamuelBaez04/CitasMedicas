package com.citasmed.citasmed.infrastructure.adapter.out.persistence;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.CitaEntity;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper.CitaMapper;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.repository.CitaJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Lecturas transaccionales: el mapper accede a relaciones LAZY (paciente, medico),
// que solo se pueden cargar con la sesión de Hibernate abierta.
@Component
@Transactional(readOnly = true)
public class CitaRepositoryAdapter implements CitaRepository {

    private final CitaJpaRepository jpaRepository;

    public CitaRepositoryAdapter(CitaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Cita guardar(Cita cita) {
        CitaEntity entity = CitaMapper.aEntity(cita);
        CitaEntity guardada = jpaRepository.save(entity);
        return CitaMapper.aDominio(guardada);
    }

    @Override
    public Optional<Cita> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(CitaMapper::aDominio);
    }

    @Override
    public List<Cita> buscarPorMedico(UUID medicoId) {
        return jpaRepository.findByMedicoId(medicoId).stream()
                .map(CitaMapper::aDominio)
                .toList();
    }

    @Override
    public List<Cita> buscarPorPaciente(UUID pacienteId) {
        return jpaRepository.findByPacienteId(pacienteId).stream()
                .map(CitaMapper::aDominio)
                .toList();
    }


    @Override
    public List<Cita> buscarQueRequierenRecordatorio() {
        LocalDateTime ahora = LocalDateTime.now();
        return jpaRepository
                .findByEstadoAndRecordatorioEnviadoFalseAndFechaHoraBetween(EstadoCita.CONFIRMADA.name(), ahora, ahora.plusHours(24))
                .stream()
                .map(CitaMapper::aDominio)
                .toList();
    }

}