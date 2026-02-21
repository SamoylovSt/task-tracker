package ru.samoylov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.samoylov.backend.service.AuthService;
import ru.samoylov.backend.dto.JwtResponse;
import ru.samoylov.backend.dto.RegistrationRequest;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<JwtResponse> register(@RequestBody RegistrationRequest request) {
        JwtResponse jwtResponse = authService.register(request);
        return ResponseEntity.ok(jwtResponse);
    }

}
