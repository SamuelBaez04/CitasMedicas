package com.citasmed.citasmed.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Paciente {

    private final UUID id;
    private final String nombre;
    private final String email;
    private final String telefono;

    public Paciente(UUID id, String nombre, String email, String telefono){
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo");
        this.nombre = validarNombre(nombre);
        this.email = validarEmail(email);
        this.telefono = telefono;
    }

    private String validarNombre(String nombre){
        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre del paciente no puede estar vacio");
        }
        return nombre;
    }

    private String validarEmail(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("El email ingresado no es valido");
        }
        return email;
    }

    public UUID getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getEmail(){ return email; }
    public String getTelefono(){ return telefono; }

}
