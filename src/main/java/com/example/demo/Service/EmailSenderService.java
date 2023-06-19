package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailBienvenida(String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ezlink.noreply@gmail.com");
        message.setTo(receiver);
        message.setText("Bienvenido a EzLink");
        message.setSubject("Te has registrado correctamente a EzLink");
        mailSender.send(message);
        System.out.println("Mail enviado...");
    }
}
