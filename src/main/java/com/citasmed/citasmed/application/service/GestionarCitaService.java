package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.exception.CitaNoEncontradaException;
import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.port.in.GestionarCita;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GestionarCitaService implements GestionarCita {

    private final CitaRepository citaRepository;

    public GestionarCitaService(CitaRepository citaRepository){
        this.citaRepository = citaRepository;
    }

    @Override
    public Cita confirmar(UUID citaId) {
        Cita cita = obtenerCita(citaId);
        cita.confirmar();
        return citaRepository.guardar(cita);
    }

    @Override
    public Cita cancelar(UUID citaId) {
        Cita cita = obtenerCita(citaId);
        cita.cancelar();
        return citaRepository.guardar(cita);
    }

    @Override
    public Cita completar(UUID citaId) {
        Cita cita = obtenerCita(citaId);
        cita.completar();
        return citaRepository.guardar(cita);
    }

    private Cita obtenerCita(UUID citaId) {
        return citaRepository.buscarPorId(citaId)
                .orElseThrow(() -> new CitaNoEncontradaException(citaId));
    }
}
