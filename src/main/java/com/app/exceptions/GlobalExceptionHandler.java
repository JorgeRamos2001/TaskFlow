package com.app.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest request) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred on the server. Please try again.",
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("Exception: {}", ex.getMessage(), ex);

        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.warn("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleResourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest request) {
        log.warn("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.warn("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidOperationException(InvalidOperationException ex, WebRequest request) {
        log.warn("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.warn("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.FORBIDDEN.value(),
                "Access denied for this resource.",
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.warn("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed. Please try again.",
                LocalDateTime.now(),
                request.getDescription(false)), HttpStatus.UNAUTHORIZED);
    }
}
