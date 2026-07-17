package com.citasmed.citasmed.infrastructure.adapter.in.web.controller;


import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.in.RegistrarPaciente;
import com.citasmed.citasmed.infrastructure.adapter.in.web.dto.PacienteRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final RegistrarPaciente registrarPaciente;

    public PacienteController(RegistrarPaciente registrarPaciente){
        this.registrarPaciente = registrarPaciente;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paciente registrarPaciente(@Valid @RequestBody PacienteRequest request){
        return registrarPaciente.registrarPaciente(request.nombre(), request.email(), request.telefono());
    }



}
