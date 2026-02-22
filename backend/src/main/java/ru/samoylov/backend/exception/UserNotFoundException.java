package ru.samoylov.backend.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException{

    private final HttpStatus status;
    private final String userMessage;

    public UserNotFoundException(HttpStatus status, String userMessage, Throwable cause) {
        super(status, userMessage, cause);
        this.status=status;
        this.userMessage=userMessage;
    }

    public UserNotFoundException(HttpStatus status, String userMessage) {
        super(status, userMessage);
        this.status=status;
        this.userMessage=userMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
