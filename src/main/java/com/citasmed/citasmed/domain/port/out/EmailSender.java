package com.citasmed.citasmed.domain.port.out;

public interface EmailSender {

    void enviar(String destinatario, String asunto, String cuerpo);

}
