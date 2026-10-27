package com.citasmed.citasmed.application.service;


import com.citasmed.citasmed.domain.model.Cita;
import com.citasmed.citasmed.domain.port.in.EnviarRecordatorios;
import com.citasmed.citasmed.domain.port.out.CitaRepository;
import com.citasmed.citasmed.domain.port.out.NotificationPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnviarRecordatoriosService implements EnviarRecordatorios {

    public static final Logger log = LoggerFactory.getLogger(EnviarRecordatoriosService.class);

    private final CitaRepository citaRepository;

    private final NotificationPublisher notificationPublisher;

    public EnviarRecordatoriosService(CitaRepository citaRepository, NotificationPublisher notificationPublisher){
        this.citaRepository = citaRepository;
        this.notificationPublisher = notificationPublisher;
    }

    @Override
    public int enviarPendientes(){
        List<Cita> citas = citaRepository.buscarQueRequierenRecordatorio();
        int enviados = 0;

        for(Cita cita : citas){
            try{
                notificationPublisher.publicarRecordatorio(cita);
                cita.marcarRecordatorioEnviado();
                citaRepository.guardar(cita);
                enviados++;
            }catch (RuntimeException e){
                log.warn("No se pudo enviar el recordatorio de la cita {} : {}", cita.getId(), e.getMessage());
            }
        }
        return enviados;
    }


}
