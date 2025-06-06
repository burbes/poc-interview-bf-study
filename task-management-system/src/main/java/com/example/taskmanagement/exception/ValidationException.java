package com.example.taskmanagement.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Exception thrown when validation errors occur.
 */
public class ValidationException extends ApiException {

    private final Map<String, String> errors;

    public ValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        this.errors = null;
    }

    public ValidationException(String message, Map<String, String> errors) {
        super(HttpStatus.BAD_REQUEST, message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

