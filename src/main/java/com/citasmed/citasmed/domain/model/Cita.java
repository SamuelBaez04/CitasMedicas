package com.citasmed.citasmed.domain.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Cita {

    private final UUID id;
    private final Medico medico;
    private final Paciente paciente;
    private final LocalDateTime fechaHora;
    private final String motivo;
    private EstadoCita estado;

    public Cita(UUID id, Paciente paciente, Medico medico, LocalDateTime fechaHora, String motivo) {
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo");
        this.paciente = Objects.requireNonNull(paciente, "El paciente es obligatorio");
        this.medico = Objects.requireNonNull(medico, "El médico es obligatorio");
        this.fechaHora = validarFecha(fechaHora);
        this.motivo = motivo;
        this.estado = EstadoCita.PENDIENTE;
    }

    private LocalDateTime validarFecha(LocalDateTime fechaHora){
        if(fechaHora == null){
            throw new IllegalArgumentException("La fecha de la cita es obligatoria");
        }
        if(fechaHora.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("La fecha ingresada no es valida");
        }
        return fechaHora;
    }

    public void confirmar() {
        if (estado != EstadoCita.PENDIENTE) {
            throw new IllegalStateException("Solo una cita PENDIENTE puede confirmarse");
        }
        this.estado = EstadoCita.CONFIRMADA;
    }

    public void cancelar() {
        if (estado == EstadoCita.COMPLETADA) {
            throw new IllegalStateException("No se puede cancelar una cita ya completada");
        }
        this.estado = EstadoCita.CANCELADA;
    }

    public void completar() {
        if (estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo una cita CONFIRMADA puede completarse");
        }
        this.estado = EstadoCita.COMPLETADA;
    }

    public boolean requiereRecordatorio(){
        LocalDateTime en24hrs = LocalDateTime.now().plusHours(24);
        return estado == EstadoCita.CONFIRMADA && fechaHora.isBefore(en24hrs);
    }

    public UUID getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public EstadoCita getEstado() { return estado; }


}
