package com.citasmed.citasmed.infrastructure.adapter.out.persistence.repository;

import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PacienteJpaRepository extends JpaRepository<PacienteEntity, UUID> {

    Optional<PacienteEntity> findByEmail(String email);
}