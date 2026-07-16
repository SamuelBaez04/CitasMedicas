package com.citasmed.citasmed.domain.exception;

import java.util.UUID;

public class CitaNoEncontradaException extends RuntimeException{

    public CitaNoEncontradaException(UUID id) {
        super("No se encontró la cita con id: " + id);
    }

}
