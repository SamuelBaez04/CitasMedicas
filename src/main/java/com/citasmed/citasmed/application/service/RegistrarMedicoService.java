package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.port.in.RegistrarMedico;
import com.citasmed.citasmed.domain.port.out.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrarMedicoService implements RegistrarMedico {

    private final MedicoRepository medicoRepository;

    public RegistrarMedicoService(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Medico registrarMedico(String nombre, String especialidad, String email) {
        Medico medico = new Medico(UUID.randomUUID(), nombre, especialidad, email);
        return medicoRepository.guardar(medico);
    }

}
