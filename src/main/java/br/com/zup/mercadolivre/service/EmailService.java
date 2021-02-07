package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.controller.ProductQuestionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductQuestionController.class);

    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender emailSender;

    public void send(String to, String from, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            LOGGER.error("Error description: " + exception.getMessage());
        }
    }
}
