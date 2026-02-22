package ru.samoylov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.samoylov.backend.dto.JwtResponse;
import ru.samoylov.backend.dto.RegistrationRequest;
import ru.samoylov.backend.dto.UserResponse;
import ru.samoylov.backend.service.TaskService;
import ru.samoylov.backend.service.UserService;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final TaskService taskService;
    private final ModelMapper mapper;

    @PostMapping("/user")
    public ResponseEntity<JwtResponse> register(@RequestBody RegistrationRequest request) {
        JwtResponse jwtResponse = userService.register(request);
        return ResponseEntity.ok(jwtResponse);
    }


    @GetMapping("/user")
    public ResponseEntity<UserResponse> getProfile(
            @AuthenticationPrincipal UserDetails userDetails
) {
        UserResponse response = mapper.map(userService.getCurrentUserByEmail(userDetails),
                UserResponse.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<JwtResponse> login(@RequestBody RegistrationRequest request) {
        JwtResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }



}
