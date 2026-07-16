package com.citasmed.citasmed.infrastructure.adapter.out.persistence;

import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.out.PacienteRepository;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper.PacienteMapper;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.repository.PacienteJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PacienteRepositoryAdapter implements PacienteRepository {

    private final PacienteJpaRepository jpaRepository;

    public PacienteRepositoryAdapter(PacienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        var entity = PacienteMapper.aEntity(paciente);
        return PacienteMapper.aDominio(jpaRepository.save(entity));
    }

    @Override
    public Optional<Paciente> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(PacienteMapper::aDominio);
    }

    @Override
    public Optional<Paciente> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email).map(PacienteMapper::aDominio);
    }
}
