package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.port.in.ConsultarCita;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConsultarCitaService implements ConsultarCita {

    private final CitaRepository citaRepository;

    public ConsultarCitaService(CitaRepository citaRepository){
        this.citaRepository = citaRepository;
    }

    @Override
    public List<Cita> porPaciente(UUID pacienteId) {
        return citaRepository.buscarPorPaciente(pacienteId);
    }

    @Override
    public List<Cita> porMedico(UUID medicoId) {
        return citaRepository.buscarPorMedico(medicoId);
    }
}
