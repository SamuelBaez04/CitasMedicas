package com.citasmed.citasmed.domain.port.in.messaging;

import com.citasmed.citasmed.domain.port.in.ProcesarRecordatorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RecordatorioListener {

    private static final Logger log = LoggerFactory.getLogger(RecordatorioListener.class);
    private final ProcesarRecordatorio procesarRecordatorio;

    public RecordatorioListener(ProcesarRecordatorio procesarRecordatorio){
        this.procesarRecordatorio = procesarRecordatorio;
    }

    @RabbitListener(queues = "${citasmed.recordatorios.colas}")
    public void recibir(String citaId){
        UUID id;
        try{
            id = UUID.fromString(citaId);
        }catch (IllegalArgumentException e){
            log.warn("Mensaje descartado: '{}' no es un id de cita valido", citaId);
            return;
        }
        procesarRecordatorio.procesar(id);
    }


}
