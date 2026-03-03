package ru.samoylov.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.samoylov.commondto.EmailTask;
import ru.samoylov.scheduler.entity.Task;
import ru.samoylov.scheduler.entity.User;
import ru.samoylov.scheduler.repository.TaskRepository;
import ru.samoylov.scheduler.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EmailTaskProducer emailTaskProducer;

    @Scheduled(cron = "0 0 0 * * *")
    public void createMessage() {
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            List<Task> taskList = taskRepository.getTasksByUser(user);
            StringBuilder unfinishedBuilder = new StringBuilder();
            StringBuilder finishedBuilder = new StringBuilder();

            int finishedCount = 0;
            int unfinishedCount = 0;
            Date now = new Date();
            long oneDayInMillis = 24 * 60 * 60 * 1000L;
            for (Task task : taskList) {
                if (task.getStatus().equals("NEW")) {
                    unfinishedCount++;
                    unfinishedBuilder.append("\n• ").append(task.getTitle());

                } else if (task.getStatus().equals("DONE") &&(now.getTime() - task.getCompletedTaskTime().getTime()<=oneDayInMillis) ) {
                    finishedCount++;
                    finishedBuilder.append("\n• ").append(task.getTitle());
                }
            }

            StringBuilder email = new StringBuilder();
            if (unfinishedCount > 0) {
                email.append("У вас осталось ")
                        .append(unfinishedCount).append(" несделанных задач:")
                        .append(unfinishedBuilder)
                        .append("\n");
            }
            if (finishedCount > 0) {
                email.append("За сегодня вы выполнили ")
                        .append(finishedCount).append(" задач:")
                        .append(finishedBuilder);
            }
            EmailTask emailTask = new EmailTask();
            emailTask.setEmail(user.getEmail());
            emailTask.setTitle("Отчёт о  проделанных задачах");
            emailTask.setDescription(email.toString());

            emailTaskProducer.sendEmailTask(emailTask);
        }

    }


}
