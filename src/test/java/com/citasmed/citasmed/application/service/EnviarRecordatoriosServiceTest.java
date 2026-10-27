package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.model.Medico;
import com.citasmed.citasmed.domain.model.Paciente;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.domain.port.out.NotificationPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class EnviarRecordatoriosServiceTest {

    @Mock private CitaRepository citaRepository;
    @Mock private NotificationPublisher notificationPublisher;

    @InjectMocks private EnviarRecordatoriosService service;

    private final Paciente paciente = new Paciente(UUID.randomUUID(), "Samuel", "samuel@email.com", "3122169275");
    private final Medico medico = new Medico(UUID.randomUUID(), "Dr. Agudelo", "Medicina General", "agudelo.email.com");

    private Cita citaConfirmadaProxima(){
        return Cita.reconstruir(UUID.randomUUID(), paciente, medico,
                LocalDateTime.now().plusHours(5), "Control", EstadoCita.CONFIRMADA,false);
    }

    @Test
    void publicarMarcaYGuardaCadaCita(){
        Cita cita = citaConfirmadaProxima();
        when(citaRepository.buscarQueRequierenRecordatorio()).thenReturn(List.of(cita));

        int enviados = service.enviarPendientes();
        assertThat(enviados).isEqualTo(1);
        assertThat(cita.isRecordatorioEnviado()).isTrue();
        verify(notificationPublisher).publicarRecordatorio(cita);
        verify(citaRepository).guardar(cita);
    }

    @Test
    void siUnaCitaFallaLasDemasSiSeEnvianYLaFallidaNoSeMarca(){
        Cita fallida = citaConfirmadaProxima();
        Cita buena = citaConfirmadaProxima();
        when(citaRepository.buscarQueRequierenRecordatorio()).thenReturn(List.of(fallida,buena));
        doThrow(new RuntimeException("broken caido")).when(notificationPublisher).publicarRecordatorio(fallida);

        int enviados = service.enviarPendientes();

        assertThat(enviados).isEqualTo(1);
        assertThat(fallida.isRecordatorioEnviado()).isFalse();
        assertThat(buena.isRecordatorioEnviado()).isTrue();
        verify(citaRepository, never()).guardar(fallida);
        verify(citaRepository).guardar(buena);
    }




}
