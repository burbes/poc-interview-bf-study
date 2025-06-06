package com.example.taskmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Base exception class for API errors.
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}

