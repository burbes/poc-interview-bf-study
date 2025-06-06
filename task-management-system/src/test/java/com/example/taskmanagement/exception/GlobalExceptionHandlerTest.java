package com.example.taskmanagement.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final WebRequest request = mock(WebRequest.class);

    // Dummy method for MethodParameter
    private static class Dummy {
        public void dummyMethod(String arg) {}
    }

    @Test
    void handleApiException_returnsExpectedResponse() {
        ApiException ex = new ApiException(HttpStatus.BAD_REQUEST, "API error");
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleApiException(ex, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleResourceNotFoundException_returnsNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleResourceNotFoundException(ex, request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleValidationException_returnsBadRequest() {
        ValidationException ex = new ValidationException("Validation failed", Map.of("field", "error"));
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleValidationException(ex, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleMethodArgumentNotValid_returnsBadRequest() throws NoSuchMethodException {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "must not be null");
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));
        MethodParameter param = new MethodParameter(Dummy.class.getMethod("dummyMethod", String.class), 0);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(param, bindingResult);
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleMethodArgumentNotValid(ex, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleConstraintViolation_returnsBadRequest() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        jakarta.validation.Path path = mock(jakarta.validation.Path.class);
        when(path.toString()).thenReturn("field");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("must not be null");
        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleConstraintViolation(ex, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleAccessDenied_returnsForbidden() {
        AccessDeniedException ex = new AccessDeniedException("Denied");
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleAccessDenied(ex, request);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void handleBadCredentials_returnsUnauthorized() {
        org.springframework.security.authentication.BadCredentialsException ex = new org.springframework.security.authentication.BadCredentialsException("Bad creds");
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleBadCredentials(ex, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void handleGlobalException_returnsInternalServerError() {
        Exception ex = new Exception("Unexpected");
        when(request.getDescription(false)).thenReturn("uri=/test");
        ResponseEntity<?> response = handler.handleGlobalException(ex, request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
} 