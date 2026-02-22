package ru.samoylov.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samoylov.backend.entity.Task;
import ru.samoylov.backend.entity.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByUser(User user);
}
