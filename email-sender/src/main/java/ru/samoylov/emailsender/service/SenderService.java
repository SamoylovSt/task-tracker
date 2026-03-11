package ru.samoylov.emailsender.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.samoylov.emailsender.dto.EmailTask ;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private static String FROM_EMAIL;

    public void sendEmail(EmailTask emailTask) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(emailTask.getEmail());
            message.setSubject(emailTask.getTitle());
            message.setText(emailTask.getDescription());

            javaMailSender.send(message);
            log.info("сообщение отправлено");
        } catch (MailException ex) {
            throw new RuntimeException("Не удалось отправить email", ex);
        }


    }
}
