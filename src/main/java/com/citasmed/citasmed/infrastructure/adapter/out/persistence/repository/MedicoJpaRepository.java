package com.citasmed.citasmed.infrastructure.adapter.out.persistence.repository;

import com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicoJpaRepository extends JpaRepository<MedicoEntity, UUID> {
}