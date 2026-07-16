package com.citasmed.citasmed.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Medico {

    private final UUID id;
    private final String nombre;
    private final String especialidad;
    private final String email;

    public Medico(UUID id, String nombre, String especialidad, String email){
        this.id = Objects.requireNonNull(id, "El id no puede ser vacio");
        this.nombre = validarNombre(nombre);
        this.especialidad = especialidad;
        this.email = email;
    }

    private String validarNombre(String nombre){
        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre del medico no puede estar vacio");
        }
        return nombre;
    }

    public String getNombre(){ return nombre;}
    public UUID getId(){ return id; }
    public String getEspecialidad(){ return especialidad; }
    public String getEmail() { return email; }
}
