package ru.samoylov.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samoylov.scheduler.entity.Task;
import ru.samoylov.scheduler.entity.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByUser(User user);

    void removeTaskById(Long id);

    boolean existsTaskById(Long id);
}
