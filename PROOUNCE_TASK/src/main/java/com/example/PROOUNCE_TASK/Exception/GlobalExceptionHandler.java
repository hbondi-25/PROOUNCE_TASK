package com.example.PROOUNCE_TASK.Exception;

import com.example.PROOUNCE_TASK.RespoceStucture;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice // Part E: Centralize errors
public class GlobalExceptionHandler {

    // Part E: Handles @Valid for Student (name/email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespoceStucture<ErrorResponse>> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                details,
                request.getRequestURI()
        );

        RespoceStucture<ErrorResponse> response = new RespoceStucture<>(
            HttpStatus.BAD_REQUEST.value(), 
            "Validation Error", 
            errorResponse
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handles 404 errors from Part A and B
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RespoceStucture<ErrorResponse>> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                List.of(ex.getMessage()),
                request.getRequestURI()
        );

        RespoceStucture<ErrorResponse> response = new RespoceStucture<>(
            HttpStatus.NOT_FOUND.value(), 
            ex.getMessage(), 
            errorResponse
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}