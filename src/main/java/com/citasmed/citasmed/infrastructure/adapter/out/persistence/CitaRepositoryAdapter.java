package com.citasmed.citasmed.infrastructure.adapter.out.persistence;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.CitaEntity;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper.CitaMapper;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.repository.CitaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CitaRepositoryAdapter implements CitaRepository {

    private final CitaJpaRepository jpaRepository;

    public CitaRepositoryAdapter(CitaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
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
                .collect(Collectors.toList());
    }

    @Override
    public List<Cita> buscarPorPaciente(UUID pacienteId) {
        return jpaRepository.findByPacienteId(pacienteId).stream()
                .map(CitaMapper::aDominio)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cita> buscarQueRequierenRecordatorio() {
        return jpaRepository.findAll().stream()
                .map(CitaMapper::aDominio)
                .filter(Cita::requiereRecordatorio)
                .collect(Collectors.toList());
    }

}
