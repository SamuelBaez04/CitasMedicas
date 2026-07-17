package com.citasmed.citasmed.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PacienteRequest(
        @NotBlank(message = "el nombre es obligatorio") String nombre,
        @Email(message = "el email no es valido") @NotBlank String email,
        String telefono
) {
}
