package com.citasmed.citasmed.infrastructure.adapter.in.web.controller;

import com.citasmed.citasmed.domain.exception.CitaNoEncontradaException;
import com.citasmed.citasmed.domain.exception.MedicoNoDisponibleException;
import com.citasmed.citasmed.domain.exception.MedicoNoEncontradoException;
import com.citasmed.citasmed.domain.exception.PacienteNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PacienteNoEncontradoException.class, MedicoNoEncontradoException.class,
            CitaNoEncontradaException.class})
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(RuntimeException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({MedicoNoDisponibleException.class, IllegalStateException.class,
            IllegalArgumentException.class})
    public ResponseEntity<Map<String, Object>> manejarConflicto(RuntimeException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return construirRespuesta(HttpStatus.BAD_REQUEST, mensaje);
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", mensaje
        ));
    }


}
