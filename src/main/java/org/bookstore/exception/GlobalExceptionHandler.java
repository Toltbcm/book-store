package org.bookstore.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, Object> body = prepareBody(HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult()
                .getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        body.put("errors", errors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Throwable throwable) {
        Map<String, Object> body = prepareBody(HttpStatus.INTERNAL_SERVER_ERROR);
        String errorMessage = getRootCauseMessage(throwable);
        body.put("message", errorMessage != null ? errorMessage : "Unknown error occurred");
        return ResponseEntity.internalServerError().body(body);
    }

    private Map<String, Object> prepareBody(HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value() + ": " + status.getReasonPhrase());
        return body;
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return fieldError.getField() + " " + error.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }

    private String getRootCauseMessage(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause.getMessage();
    }
}
