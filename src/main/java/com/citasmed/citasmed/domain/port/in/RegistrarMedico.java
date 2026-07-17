package com.citasmed.citasmed.domain.port.in;


import com.citasmed.citasmed.domain.model.Medico;

public interface RegistrarMedico {

    Medico registrarMedico(String nombre, String especialidad, String email);

}
