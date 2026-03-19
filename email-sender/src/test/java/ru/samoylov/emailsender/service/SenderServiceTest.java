package ru.samoylov.emailsender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import ru.samoylov.emailsender.dto.EmailTask;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SenderServiceTest {

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private SenderService senderService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(
                SenderService.class,
                "FROM_EMAIL",
                "noreply@test.com"
        );
    }

    @Test
    void shouldSendEmailSuccessfully() {
        EmailTask emailTask = new EmailTask();
        emailTask.setEmail("test@test.com");
        emailTask.setTitle("Test Subject");
        emailTask.setDescription("Test Description");

        senderService.sendEmail(emailTask);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(captor.capture());

        SimpleMailMessage message = captor.getValue();
        assertThat(message.getFrom()).isEqualTo("noreply@test.com");
    }

    @Test
    void shouldThrowRuntimeExceptionWhenMailSendingFails() {
        EmailTask emailTask = new EmailTask();
        emailTask.setEmail("user@test.com");
        emailTask.setTitle("Test Subject");
        emailTask.setDescription("Test Description");

        doThrow(new MailException("SMTP connection failed") {
        })
                .when(javaMailSender).send(any(SimpleMailMessage.class));

        assertThatThrownBy(() -> senderService.sendEmail(emailTask))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Не удалось отправить email")
                .hasCauseInstanceOf(MailException.class);
    }

}
