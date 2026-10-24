package com.citasmed.citasmed.infrastructure.adapter.in.web.controller;

import com.citasmed.citasmed.domain.port.in.RegistrarMedico;
import com.citasmed.citasmed.infrastructure.adapter.in.web.dto.MedicoRequest;
import com.citasmed.citasmed.infrastructure.adapter.in.web.dto.MedicoResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Medicos", description = "Registro de Medicos")
@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final RegistrarMedico registrarMedico;

    public MedicoController(RegistrarMedico registrarMedico){
        this.registrarMedico = registrarMedico;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoResponse registrarMedico(@Valid @RequestBody MedicoRequest request){
        return MedicoResponse.desde(
                registrarMedico.registrarMedico(request.nombre(), request.especialidad(), request.email()));
    }

}