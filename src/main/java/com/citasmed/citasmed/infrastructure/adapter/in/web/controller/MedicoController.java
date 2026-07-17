package com.citasmed.citasmed.infrastructure.adapter.in.web.controller;


import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.port.in.RegistrarMedico;
import com.citasmed.citasmed.infrastructure.adapter.in.web.dto.MedicoRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final RegistrarMedico registrarMedico;

    public MedicoController(RegistrarMedico registrarMedico){
        this.registrarMedico = registrarMedico;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medico registrarMedico(@Valid @RequestBody MedicoRequest request){
        return registrarMedico.registrarMedico(request.nombre(), request.especialidad(), request.email());
    }


}
