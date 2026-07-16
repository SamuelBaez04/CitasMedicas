package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.exception.MedicoNoDisponibleException;
import com.citasmed.citasmed.domain.exception.MedicoNoEncontradoException;
import com.citasmed.citasmed.domain.exception.PacienteNoEncontradoException;
import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.in.AgendarCita;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.domain.port.out.MedicoRepository;
import com.citasmed.citasmed.domain.port.out.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AgendarCitaService implements AgendarCita {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public AgendarCitaService(CitaRepository citaRepository,
                              PacienteRepository pacienteRepository,
                              MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Cita agendar(UUID pacienteId, UUID medicoId, LocalDateTime fechaHora, String motivo) {
        Paciente paciente = pacienteRepository.buscarPorId(pacienteId)
                .orElseThrow(() -> new PacienteNoEncontradoException(pacienteId));

        Medico medico = medicoRepository.buscarPorId(medicoId)
                .orElseThrow(() -> new MedicoNoEncontradoException(medicoId));

        validarDisponibilidad(medicoId, fechaHora);

        Cita cita = new Cita(UUID.randomUUID(), paciente, medico, fechaHora, motivo);

        return citaRepository.guardar(cita);
    }

    private void validarDisponibilidad(UUID medicoId, LocalDateTime fechaHora) {
        List<Cita> citasDelMedico = citaRepository.buscarPorMedico(medicoId);

        boolean yaOcupado = citasDelMedico.stream()
                .filter(c -> c.getEstado() != EstadoCita.CANCELADA)
                .anyMatch(c -> c.getFechaHora().equals(fechaHora));

        if (yaOcupado) {
            throw new MedicoNoDisponibleException(medicoId, fechaHora);
        }
    }
}