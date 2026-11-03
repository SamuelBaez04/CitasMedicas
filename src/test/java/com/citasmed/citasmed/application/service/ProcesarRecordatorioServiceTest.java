package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.domain.port.out.EmailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcesarRecordatorioServiceTest {

    @Mock private CitaRepository citaRepository;
    @Mock private EmailSender emailSender;

    @InjectMocks private ProcesarRecordatorioService service;

    private final Paciente paciente = new Paciente(UUID.randomUUID(), "Samuel Valencia", "samuel@email.com", "3122169275");
    private final Medico medico = new Medico(UUID.randomUUID(), "Dra. Tibaduiza", "Medicina General", "tibaduiza@email.com");

    private Cita cita(EstadoCita estado){
        return Cita.reconstruir(UUID.randomUUID(), paciente, medico,
                LocalDateTime.now().plusHours(5), "Control", estado, false);
    }

    @Test
    void enviarElCorreoAlPacienteSiLaCitaEstaConfirmada(){
        Cita cita = cita(EstadoCita.CONFIRMADA);
        when(citaRepository.buscarPorId(cita.getId())).thenReturn(Optional.of(cita));

        service.procesar(cita.getId());

        ArgumentCaptor<String> cuerpo = ArgumentCaptor.forClass(String.class);
        verify(emailSender).enviar(eq("samuel@email.com"), anyString(), cuerpo.capture());
        assertThat(cuerpo.getValue()).contains("Samuel Valencia").contains("Dra. Tibaduiza").contains("Control");
    }

    @Test
    void noEnviaCorreoSiLaCitaFueCancelada() {
        Cita cita = cita(EstadoCita.CANCELADA);
        when(citaRepository.buscarPorId(cita.getId())).thenReturn(Optional.of(cita));

        service.procesar(cita.getId());

        verifyNoInteractions(emailSender);
    }

    @Test
    void noEnviaCorreoSiLaCitaYaNoExiste() {
        UUID id = UUID.randomUUID();
        when(citaRepository.buscarPorId(id)).thenReturn(Optional.empty());

        service.procesar(id);

        verifyNoInteractions(emailSender);
    }

}
