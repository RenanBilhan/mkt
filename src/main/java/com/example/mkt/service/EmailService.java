package com.example.mkt.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import freemarker.template.Configuration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class EmailService {

    private final JavaMailSender mailSender;
    private final Configuration fmConfiguration;


    @Value("${spring.mail.username}")
    private String username;

    public void sendNote() throws MessagingException {
        MimeMessage email = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(email, true);

        helper.setFrom("renan.bilhan@dbccompany.com.br");
        helper.setTo("renan.bilhan@hotmail.com");
        helper.setSubject("Nota fiscal");
        helper.setText("Compra realizada! Segue em anexo a nota fiscal.");

        mailSender.send(email);
    }
}
