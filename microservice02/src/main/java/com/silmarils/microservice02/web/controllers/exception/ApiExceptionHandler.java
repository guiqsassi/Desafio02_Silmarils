package com.silmarils.microservice02.web.controllers.exception;
import com.silmarils.microservice02.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFound(RuntimeException exception, HttpServletRequest request) {
        log.error("Api, Error -", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        StringBuilder errorMessage = new StringBuilder();
        errors.forEach((field, message) ->
                errorMessage.append(field).append(": ").append(message).append(". "));

        ErrorMessage errorResponse = new ErrorMessage(
                request,
                HttpStatus.BAD_REQUEST,
                errorMessage.toString()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("Api, Error - URI: {}, Message: {}", request.getRequestURI(), ex.getMessage(), ex);
        String errorMessage = String.format("Error processing request at %s: %s", request.getRequestURI(), ex.getMessage());

        ErrorMessage errorResponse = new ErrorMessage(
                request,
                HttpStatus.BAD_REQUEST,
                errorMessage
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
