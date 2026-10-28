package com.citasmed.citasmed.infrastructure.config;

import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue colaRecordatorios(@Value("${citasmed.recordatorios.cola}") String nombre){
        return QueueBuilder.durable(nombre).build();
    }




}
