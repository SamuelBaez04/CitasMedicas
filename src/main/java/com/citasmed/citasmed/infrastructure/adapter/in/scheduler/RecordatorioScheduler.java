package com.citasmed.citasmed.infrastructure.adapter.in.scheduler;

import com.citasmed.citasmed.domain.port.in.EnviarRecordatorios;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "citasmed.recordatorios.habilitado", havingValue = "true", matchIfMissing = true)
public class RecordatorioScheduler {

    private static final Logger log = LoggerFactory.getLogger(RecordatorioScheduler.class);

    private final EnviarRecordatorios enviarRecordatorios;

    public RecordatorioScheduler(EnviarRecordatorios enviarRecordatorios){
        this.enviarRecordatorios = enviarRecordatorios;
    }

    @Scheduled(fixedDelayString = "${citasmed.recordatorios.intervalo-ms:60000}")
    public void ejecutar(){
        int enviados = enviarRecordatorios.enviarPendientes();
        if(enviados > 0){
            log.info("Recordatorios enviados en este ciclo: {}",enviados);
        }
    }


}
