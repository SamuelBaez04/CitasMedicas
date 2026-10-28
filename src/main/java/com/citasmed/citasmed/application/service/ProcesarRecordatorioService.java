package com.citasmed.citasmed.application.service;

import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.model.EstadoCita;
import com.citasmed.citasmed.domain.port.in.ProcesarRecordatorio;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProcesarRecordatorioService implements ProcesarRecordatorio {

    private static final Logger log = LoggerFactory.getLogger(ProcesarRecordatorioService.class);
    private final CitaRepository citaRepository;

    public ProcesarRecordatorioService(CitaRepository citaRepository){
        this.citaRepository=citaRepository;
    }

    @Override
    public void procesar(UUID citaId){
        Optional<Cita> encontrada = citaRepository.buscarPorId(citaId);
        if(encontrada.isEmpty()){
            log.warn("Recordatorio descartado: la cita {} ya no existe", citaId);
            return;
        }
        Cita cita = encontrada.get();
        if(cita.getEstado() != EstadoCita.CONFIRMADA){
           log.info("Recordatorio descartado: la cita {} esta {}", citaId, cita.getEstado());
           return;
        }

        log.info("Procesando recordatorio de la cita {} para {}", citaId, cita.getPaciente());

    }


}
