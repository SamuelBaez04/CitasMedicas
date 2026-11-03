package com.citasmed.citasmed.infrastructure.adapter.out.email;

import com.citasmed.citasmed.domain.port.out.EmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class SmtpEmailSender implements EmailSender {

    private final JavaMailSender mailSender;
    private final String remitente;

    public SmtpEmailSender(JavaMailSender mailSender,
                           @Value("${citasmed.correo.remitente}") String remitente){
        this.mailSender = mailSender;
        this.remitente = remitente;
    }

    @Override
    public void enviar(String destinatario, String asunto, String cuerpo){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(remitente);
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mailSender.send(mensaje);
    }

}
