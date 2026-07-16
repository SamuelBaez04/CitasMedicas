package com.citasmed.citasmed.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "medicos")
public class MedicoEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    private String especialidad;

    private String email;

    protected MedicoEntity(){}

    public MedicoEntity(UUID id, String nombre, String especialidad, String email) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public String getEmail() { return email; }

}
