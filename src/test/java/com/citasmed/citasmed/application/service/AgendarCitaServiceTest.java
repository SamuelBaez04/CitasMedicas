package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.exception.MedicoNoDisponibleException;
import com.citasmed.citasmed.domain.exception.MedicoNoEncontradoException;
import com.citasmed.citasmed.domain.exception.PacienteNoEncontradoException;
import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.domain.port.out.MedicoRepository;
import com.citasmed.citasmed.domain.port.out.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendarCitaServiceTest {

    @Mock private CitaRepository citaRepository;
    @Mock private PacienteRepository pacienteRepository;
    @Mock private MedicoRepository medicoRepository;

    @InjectMocks private AgendarCitaService service;

    private final Paciente paciente = new Paciente(UUID.randomUUID(), "Ana Gomez", "ana@mail.com", "3001234567");
    private final Medico medico = new Medico(UUID.randomUUID(), "Dr. Ruiz", "Medicina general", "ruiz@mail.com");
    private final LocalDateTime fechaHora = LocalDateTime.now().plusDays(2);

    @Test
    void agendaUnaCitaPendienteCuandoTodoEsValido() {
        when(pacienteRepository.buscarPorId(paciente.getId())).thenReturn(Optional.of(paciente));
        when(medicoRepository.buscarPorId(medico.getId())).thenReturn(Optional.of(medico));
        when(citaRepository.buscarPorMedico(medico.getId())).thenReturn(List.of());
        when(citaRepository.guardar(any(Cita.class))).thenAnswer(inv -> inv.getArgument(0));

        Cita cita = service.agendar(paciente.getId(), medico.getId(), fechaHora, "Control");

        assertThat(cita.getEstado()).isEqualTo(EstadoCita.PENDIENTE);
        assertThat(cita.getPaciente()).isEqualTo(paciente);
        assertThat(cita.getMedico()).isEqualTo(medico);
        verify(citaRepository).guardar(any(Cita.class));
    }

    @Test
    void falla_siElPacienteNoExiste() {
        when(pacienteRepository.buscarPorId(paciente.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.agendar(paciente.getId(), medico.getId(), fechaHora, "Control"))
                .isInstanceOf(PacienteNoEncontradoException.class);

        verify(citaRepository, never()).guardar(any());
    }

    @Test
    void falla_siElMedicoNoExiste() {
        when(pacienteRepository.buscarPorId(paciente.getId())).thenReturn(Optional.of(paciente));
        when(medicoRepository.buscarPorId(medico.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.agendar(paciente.getId(), medico.getId(), fechaHora, "Control"))
                .isInstanceOf(MedicoNoEncontradoException.class);

        verify(citaRepository, never()).guardar(any());
    }

    @Test
    void falla_siElMedicoYaTieneUnaCitaEnEseHorario() {
        Cita existente = new Cita(UUID.randomUUID(), paciente, medico, fechaHora, "Otra");
        when(pacienteRepository.buscarPorId(paciente.getId())).thenReturn(Optional.of(paciente));
        when(medicoRepository.buscarPorId(medico.getId())).thenReturn(Optional.of(medico));
        when(citaRepository.buscarPorMedico(medico.getId())).thenReturn(List.of(existente));

        assertThatThrownBy(() -> service.agendar(paciente.getId(), medico.getId(), fechaHora, "Control"))
                .isInstanceOf(MedicoNoDisponibleException.class);

        verify(citaRepository, never()).guardar(any());
    }

    @Test
    void unaCitaCanceladaNoBloqueaElHorario() {
        Cita cancelada = Cita.reconstruir(UUID.randomUUID(), paciente, medico, fechaHora, "Otra", EstadoCita.CANCELADA);
        when(pacienteRepository.buscarPorId(paciente.getId())).thenReturn(Optional.of(paciente));
        when(medicoRepository.buscarPorId(medico.getId())).thenReturn(Optional.of(medico));
        when(citaRepository.buscarPorMedico(medico.getId())).thenReturn(List.of(cancelada));
        when(citaRepository.guardar(any(Cita.class))).thenAnswer(inv -> inv.getArgument(0));

        Cita cita = service.agendar(paciente.getId(), medico.getId(), fechaHora, "Control");

        assertThat(cita.getEstado()).isEqualTo(EstadoCita.PENDIENTE);
    }
}