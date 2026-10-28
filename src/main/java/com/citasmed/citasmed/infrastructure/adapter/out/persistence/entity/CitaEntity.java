package com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "citas")
public class CitaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteEntity paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoEntity medico;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    private String motivo;

    @Column(nullable = false)
    private String estado;

    @Column(name = "recordatorio_enviado", nullable = false)
    private boolean recordatorioEnviado;

    protected CitaEntity() {
    }

    public CitaEntity(UUID id, PacienteEntity paciente, MedicoEntity medico,
                      LocalDateTime fechaHora, String motivo, String estado, boolean recordatorioEnviado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado;
        this.recordatorioEnviado = recordatorioEnviado;
    }

    public UUID getId() { return id; }
    public PacienteEntity getPaciente() { return paciente; }
    public MedicoEntity getMedico() { return medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
    public boolean isRecordatorioEnviado(){ return recordatorioEnviado; }
}
