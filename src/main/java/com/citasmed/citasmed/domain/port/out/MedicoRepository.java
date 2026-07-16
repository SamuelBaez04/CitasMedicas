package com.citasmed.citasmed.domain.port.out;

import com.citasmed.citasmed.domain.model.Medico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicoRepository {

    Medico guardar(Medico medico);

    Optional<Medico> buscarPorId(UUID id);

    List<Medico> listarTodos();

}
