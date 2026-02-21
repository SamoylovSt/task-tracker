package ru.samoylov.backend.exception;

import org.springframework.http.HttpStatus;

public class RegisterException extends BaseException{
   private final HttpStatus status;
   private final String userMessage;

   public RegisterException(HttpStatus status, String userMessage, Throwable cause) {
      super(status, userMessage, cause);
      this.status=status;
      this.userMessage=userMessage;
   }

   public RegisterException(HttpStatus status, String userMessage) {
      super(status, userMessage);
      this.status=status;
      this.userMessage=userMessage;
   }
}
