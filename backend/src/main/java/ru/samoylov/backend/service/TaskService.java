package ru.samoylov.backend.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.samoylov.backend.dto.TaskResponse;
import ru.samoylov.backend.entity.Task;
import ru.samoylov.backend.entity.User;
import ru.samoylov.backend.exception.BaseException;
import ru.samoylov.backend.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public List<TaskResponse> getUsersTasks(UserDetails userDetails) {
        User currentUser = userService.getCurrentUserByEmail(userDetails);
        List<Task> taskList = taskRepository.getTasksByUser(currentUser);
        return taskList.stream()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
    }

    public TaskResponse createTask(TaskResponse taskResponse, User currentUser) {
        Task taskForSave = new Task();
        taskForSave.setUser(currentUser);
        taskForSave.setDescription(taskResponse.getDescription());
        taskForSave.setTitle(taskResponse.getTitle());
        taskForSave.setStatus("NEW");
        return modelMapper.map(taskRepository.save(taskForSave), TaskResponse.class);
    }

    public TaskResponse edit(TaskResponse taskResponse) {
        Task task = taskRepository.findById(taskResponse.getId()).orElseThrow(
                () -> new BaseException(HttpStatus.NOT_FOUND, "задача не найдена")) ;

        task.setDescription(taskResponse.getDescription());
        task.setTitle(taskResponse.getTitle());
        if(taskResponse.getStatus().equals("DONE")){
            task.setStatus(taskResponse.getStatus());
            Date completedTaskTime= new Date();
            task.setCompletedTaskTime(completedTaskTime);
        }
        taskRepository.save(task);
        return modelMapper.map(task,TaskResponse.class);

    }
}
