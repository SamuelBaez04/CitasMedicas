package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.port.in.ProcesarRecordatorio;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.domain.port.out.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcesarRecordatorioService implements ProcesarRecordatorio {

    private static final Logger log = LoggerFactory.getLogger(ProcesarRecordatorioService.class);
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'a las' HH:mm", Locale.forLanguageTag("es-CO"));

    private final CitaRepository citaRepository;
    private final EmailSender emailSender;

    public ProcesarRecordatorioService(CitaRepository citaRepository, EmailSender emailSender) {
        this.citaRepository = citaRepository;
        this.emailSender = emailSender;
    }

    @Override
    public void procesar(UUID citaId) {
        Optional<Cita> encontrada = citaRepository.buscarPorId(citaId);
        if (encontrada.isEmpty()) {
            log.warn("Recordatorio descartado: la cita {} ya no existe", citaId);
            return;
        }

        Cita cita = encontrada.get();
        // Entre publicar el mensaje y consumirlo, la cita pudo cancelarse.
        if (cita.getEstado() != EstadoCita.CONFIRMADA) {
            log.info("Recordatorio descartado: la cita {} está {}", citaId, cita.getEstado());
            return;
        }

        String motivo = cita.getMotivo() == null || cita.getMotivo().isBlank()
                ? "No especificado" : cita.getMotivo();

        String cuerpo = """
                Hola %s,

                Te recordamos que tienes una cita con %s (%s) el %s.
                Motivo: %s

                Si no puedes asistir, por favor cancélala con anticipación.
                """.formatted(
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombre(),
                cita.getMedico().getEspecialidad(),
                FORMATO_FECHA.format(cita.getFechaHora()),
                motivo);

        emailSender.enviar(cita.getPaciente().getEmail(), "Recordatorio de tu cita médica", cuerpo);
        log.info("Correo de recordatorio enviado para la cita {} a {}", citaId, cita.getPaciente().getEmail());
    }
}