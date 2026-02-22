package ru.samoylov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.samoylov.backend.dto.TaskResponse;
import ru.samoylov.backend.dto.UserResponse;
import ru.samoylov.backend.entity.User;
import ru.samoylov.backend.exception.BaseException;
import ru.samoylov.backend.service.TaskService;
import ru.samoylov.backend.service.UserService;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;
    private final ModelMapper mapper;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> tasks(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.getUsersTasks(userDetails));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskResponse taskResponse,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getCurrentUserByEmail(userDetails);
        TaskResponse taskResponseForCreate = taskService.createTask(taskResponse, currentUser);
        return ResponseEntity.ok(taskResponseForCreate);
    }

    @PatchMapping("/tasks")
    public ResponseEntity<?> edit(@RequestBody TaskResponse taskResponse) {
        TaskResponse taskResponse1 = taskService.edit(taskResponse);
        return ResponseEntity.ok(taskResponse1);
    }
    //редактировать
    //удалить
}
