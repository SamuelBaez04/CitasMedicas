package com.citasmed.citasmed.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendarCitaRequest(
        @NotNull(message = "El id del paciente es obligatorio") UUID idPaciente,
        @NotNull(message = "El id del medico es obligatorio") UUID idMedico,
        @NotNull(message = "La fecha y la Hora son obligatorias")
        @Future(message = "La fecha no puede ser en el pasado")LocalDateTime fechaHora,
        String motivo
) { }
