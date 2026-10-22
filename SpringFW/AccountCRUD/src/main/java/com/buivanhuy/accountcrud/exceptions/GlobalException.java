package com.buivanhuy.accountcrud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        InvalidDataException invalidDataException = new InvalidDataException(ex);

        Map<String, Object> responseBody = Map.of(
                "status", HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "message", "Validation failed",
                "errors", invalidDataException.getErrors()
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(responseBody);
    }
}