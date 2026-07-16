package com.citasmed.citasmed.domain.exception;


import java.util.UUID;

public class MedicoNoEncontradoException extends RuntimeException{

    public MedicoNoEncontradoException(UUID id) {
        super("No se encontró el medico con id: " + id);
    }
}
