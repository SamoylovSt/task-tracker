package ru.samoylov.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.samoylov.backend.exception.BaseException;
import ru.samoylov.backend.exception.ExceptionResponse;
import ru.samoylov.backend.exception.RegisterException;
import ru.samoylov.backend.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<ExceptionResponse> conflict(RegisterException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFound(UserNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> notFound(BaseException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionResponse(ex.getMessage()));
    }

}
