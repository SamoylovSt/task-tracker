package ru.samoylov.backend.exception;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends BaseException {

    private final HttpStatus status;
    public JwtAuthenticationException(HttpStatus status, String userMessage, Throwable cause) {
        super(status, userMessage, cause);
        this.status = status;
    }

    public JwtAuthenticationException(HttpStatus status, String userMessage) {
        super(status, userMessage);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
