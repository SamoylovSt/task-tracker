package ru.samoylov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.samoylov.backend.dto.TaskResponse;
import ru.samoylov.backend.entity.User;
import ru.samoylov.backend.service.EmailTaskProducer;
import ru.samoylov.backend.service.TaskService;
import ru.samoylov.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;
    private final EmailTaskProducer producer;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> tasks(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.getUsersTasks(userDetails));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskResponse taskResponse,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getCurrentUserByEmail(userDetails);
        TaskResponse taskResponseForCreate = taskService.createTask(taskResponse, currentUser);
        return ResponseEntity.ok(taskResponseForCreate);
    }

    @PatchMapping
    public ResponseEntity<TaskResponse> edit(@RequestBody TaskResponse taskResponse) {
        TaskResponse editedTask = taskService.edit(taskResponse);
        return ResponseEntity.ok(editedTask);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody TaskResponse taskResponse) {
        taskService.deleteTaskById(taskResponse.getId());
        return ResponseEntity.ok().build();
    }

}
