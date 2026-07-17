package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.in.RegistrarPaciente;
import com.citasmed.citasmed.domain.port.out.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrarPacienteService implements RegistrarPaciente {

    private final PacienteRepository pacienteRepository;

    public RegistrarPacienteService(PacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente registrarPaciente(String nombre, String email, String telefono) {
        Paciente paciente = new Paciente(UUID.randomUUID(), nombre, email, telefono);
        return pacienteRepository.guardar(paciente);
    }

}
