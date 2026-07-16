package com.citasmed.citasmed.domain.port.out;

import com.citasmed.citasmed.domain.model.Paciente;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository {

    Paciente guardar(Paciente paciente);

    Optional<Paciente> buscarPorId(UUID id);

    Optional<Paciente> buscarPorEmail(String email);
}
