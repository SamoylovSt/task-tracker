package ru.samoylov.scheduler.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.samoylov.scheduler.dto.EmailTask;
import ru.samoylov.scheduler.entity.Task;
import ru.samoylov.scheduler.entity.User;
import ru.samoylov.scheduler.repository.TaskRepository;
import ru.samoylov.scheduler.repository.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private EmailTaskProducer emailTaskProducer;
    @InjectMocks
    private SchedulerService schedulerService;

    @Test
    void shouldDoNothingWhenNoUsers() {
        when(userRepository.findAll()).thenReturn(List.of());
        schedulerService.createMessage();
        verify(taskRepository, never()).getTasksByUser(any());
        verify(emailTaskProducer, never()).sendEmailTask(any());
    }

    @Test
    void shouldSendEmailWithEmptyDescription_whenUserHasNoTasks() {
        User user = new User();
        user.setEmail("test@test.com");


        when(userRepository.findAll()).thenReturn(List.of(user));
        when(taskRepository.getTasksByUser(user)).thenReturn(List.of());

        schedulerService.createMessage();

        verify(emailTaskProducer, times(1)).sendEmailTask(any());

        ArgumentCaptor<EmailTask> emailCaptor = ArgumentCaptor.forClass(EmailTask.class);
        verify(emailTaskProducer).sendEmailTask(emailCaptor.capture());
        EmailTask capturedEmail = emailCaptor.getValue();

        assertThat(capturedEmail.getEmail()).isEqualTo("test@test.com");
        assertThat(capturedEmail.getDescription()).isEmpty();
    }

    @Test
    void shouldSendEmailWithOnlyUnfinishedTasks_whenUserHasOnlyNewTasks() {
        User user = createUser("test@test.com");
        List<Task> newTasks = List.of(
                createTask("Купить продукты", "NEW"),
                createTask("Сделать ДЗ", "NEW")
        );
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(taskRepository.getTasksByUser(user)).thenReturn(newTasks);

        schedulerService.createMessage();

        ArgumentCaptor<EmailTask> captor = ArgumentCaptor.forClass(EmailTask.class);
        verify(emailTaskProducer).sendEmailTask(captor.capture());

        EmailTask email=captor.getValue();
        assertThat(email.getDescription())
                .contains("У вас осталось 2 несделанных задач:")
                .contains("• Купить продукты")
                .contains("• Сделать ДЗ")
                .doesNotContain("За сегодня вы выполнили");
    }

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }

    private Task createTask(String title, String status) {
        Task task = new Task();
        task.setTitle(title);
        task.setStatus(status);
        task.setCompletedTaskTime(null); // по умолчанию
        return task;
    }
}
