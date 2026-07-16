package com.citasmed.citasmed.domain.exception;

import java.util.UUID;

public class PacienteNoEncontradoException extends RuntimeException{

    public PacienteNoEncontradoException(UUID id) {
        super("No se encontró el paciente con id: " + id);
    }

}
