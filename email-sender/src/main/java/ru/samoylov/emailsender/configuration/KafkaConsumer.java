package ru.samoylov.emailsender.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.samoylov.emailsender.dto.EmailTask ;
import ru.samoylov.emailsender.service.SenderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SenderService senderService;
    private static final String TOPIC = "EMAIL_SENDING_TASKS";

    @KafkaListener(
            topics = TOPIC,
            groupId = "task-tracker"
    )
    public void listen(EmailTask emailTask) {

        senderService.sendEmail(emailTask);

    }
}
