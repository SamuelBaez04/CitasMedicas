package com.citasmed.citasmed.infrastructure.adapter.in.web.controller;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.port.in.AgendarCita;
import com.citasmed.citasmed.domain.port.in.ConsultarCita;
import com.citasmed.citasmed.domain.port.in.GestionarCita;
import com.citasmed.citasmed.infrastructure.adapter.in.web.dto.AgendarCitaRequest;
import com.citasmed.citasmed.infrastructure.adapter.in.web.dto.CitaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final AgendarCita agendarCita;
    private final GestionarCita gestionarCita;
    private final ConsultarCita consultarCita;

    public CitaController(AgendarCita agendarCita, GestionarCita gestionarCita,
                          ConsultarCita consultarCita){
        this.agendarCita = agendarCita;
        this.gestionarCita = gestionarCita;
        this.consultarCita = consultarCita;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitaResponse agendarCita(@Valid @RequestBody AgendarCitaRequest request){
        Cita cita = agendarCita.agendar(
                request.idPaciente(), request.idMedico(), request.fechaHora(), request.motivo());
        return CitaResponse.desde(cita);
    }

    @PatchMapping("/{id}/confirmar")
    public CitaResponse confirmarCita(@PathVariable UUID id){
        return CitaResponse.desde(gestionarCita.confirmar(id));
    }

    @PatchMapping("/{id}/cancelar")
    public CitaResponse cancelarCita(@PathVariable UUID id){
        return CitaResponse.desde(gestionarCita.cancelar(id));
    }

    @PatchMapping("/{id}/completar")
    public CitaResponse completarCita(@PathVariable UUID id){
        return CitaResponse.desde(gestionarCita.confirmar(id));
    }

    @GetMapping("/paciente/{idPaciente}")
    public List<CitaResponse> porPaciente(@PathVariable UUID idPaciente) {
        return consultarCita.porPaciente(idPaciente).stream()
                .map(CitaResponse::desde)
                .collect(Collectors.toList());
    }

    @GetMapping("/medico/{idMedico}")
    public List<CitaResponse> porMedico(@PathVariable UUID idMedico) {
        return consultarCita.porMedico(idMedico).stream()
                .map(CitaResponse::desde)
                .collect(Collectors.toList());
    }


}
