package ru.samoylov.backend.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final String userMessage;

    public BaseException(HttpStatus status, String userMessage, Throwable cause) {
        super(userMessage, cause);
        this.status=status;
        this.userMessage = userMessage;

    }
    public BaseException(HttpStatus status, String userMessage) {
        super(userMessage);
        this.status = status;
        this.userMessage = userMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
