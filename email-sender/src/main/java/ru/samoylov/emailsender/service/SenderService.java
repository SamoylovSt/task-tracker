package ru.samoylov.emailsender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.samoylov.commondto.EmailTask;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final JavaMailSender javaMailSender;
    private static final String FROM_EMAIL = "samoylovstv@gmail.com";
    public void sendEmail(EmailTask emailTask) {

        try {
            SimpleMailMessage message= new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(emailTask.getEmail());
            message.setSubject(emailTask.getTitle());
            message.setText(emailTask.getDescription());

            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new RuntimeException("Не удалось отправить email", ex);
        }


    }
}
