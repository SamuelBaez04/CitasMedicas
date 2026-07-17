package com.citasmed.citasmed.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record MedicoRequest(
        @NotBlank(message = "el nombre es obligatorio") String nombre,
        String especialidad,
        String email
) {
}
