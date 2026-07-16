package com.citasmed.citasmed.domain.port.out;

import com.citasmed.citasmed.domain.model.Cita;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CitaRepository {

    Cita guardar(Cita cita);

    Optional<Cita> buscarPorId(UUID id);

    List<Cita> buscarPorMedico(UUID medicoId);

    List<Cita> buscarPorPaciente(UUID pacienteId);

    List<Cita> buscarQueRequierenRecordatorio();

}
