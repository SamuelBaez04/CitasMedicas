package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.exception.CitaNoEncontradaException;
import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GestionarCitaServiceTest {

    @Mock private CitaRepository citaRepository;

    @InjectMocks private GestionarCitaService service;

    private final Paciente paciente = new Paciente(UUID.randomUUID(), "Ana Gomez", "ana@mail.com", "3001234567");
    private final Medico medico = new Medico(UUID.randomUUID(), "Dr. Ruiz", "Medicina general", "ruiz@mail.com");

    private Cita citaPendiente() {
        return new Cita(UUID.randomUUID(), paciente, medico, LocalDateTime.now().plusDays(1), "Control");
    }

    @Test
    void confirmaYGuardaLaCita() {
        Cita cita = citaPendiente();
        when(citaRepository.buscarPorId(cita.getId())).thenReturn(Optional.of(cita));
        when(citaRepository.guardar(any(Cita.class))).thenAnswer(inv -> inv.getArgument(0));

        Cita resultado = service.confirmar(cita.getId());

        assertThat(resultado.getEstado()).isEqualTo(EstadoCita.CONFIRMADA);
        verify(citaRepository).guardar(cita);
    }

    @Test
    void falla_siLaCitaNoExiste() {
        UUID id = UUID.randomUUID();
        when(citaRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.cancelar(id)).isInstanceOf(CitaNoEncontradaException.class);

        verify(citaRepository, never()).guardar(any());
    }

    @Test
    void noGuardaCuandoLaTransicionDeEstadoEsInvalida() {
        Cita cita = citaPendiente();
        when(citaRepository.buscarPorId(cita.getId())).thenReturn(Optional.of(cita));

        assertThatThrownBy(() -> service.completar(cita.getId())).isInstanceOf(IllegalStateException.class);

        verify(citaRepository, never()).guardar(any());
    }
}