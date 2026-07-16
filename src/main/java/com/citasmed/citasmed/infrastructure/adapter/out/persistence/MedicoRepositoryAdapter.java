package com.citasmed.citasmed.infrastructure.adapter.out.persistence;

import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.port.out.MedicoRepository;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.mapper.MedicoMapper;
import com.citasmed.citasmed.infrastructure.adapter.out.persistence.repository.MedicoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MedicoRepositoryAdapter implements MedicoRepository {

    private final MedicoJpaRepository jpaRepository;

    public MedicoRepositoryAdapter(MedicoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Medico guardar(Medico medico) {
        var entity = MedicoMapper.aEntity(medico);
        return MedicoMapper.aDominio(jpaRepository.save(entity));
    }

    @Override
    public Optional<Medico> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(MedicoMapper::aDominio);
    }

    @Override
    public List<Medico> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(MedicoMapper::aDominio)
                .collect(Collectors.toList());
    }
}
