package com.citasmed.citasmed.domain.port.in;

import com.citasmed.citasmed.domain.model.Paciente;

public interface RegistrarPaciente {

    Paciente registrarPaciente(String nombre, String email, String telefono);

}
