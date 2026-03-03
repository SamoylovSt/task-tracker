package ru.samoylov.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.samoylov.commondto.EmailTask;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailTaskProducer {

    private static final String TOPIC = "EMAIL_SENDING_TASKS";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEmailTask(EmailTask task) {
        kafkaTemplate.send(TOPIC, task);
        System.out.println("сообщение отправлено");
    }
}
