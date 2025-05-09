package org.example.usersubscriptionservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        log.warn("Validation error: {}", errors);
        return new ErrorResponse("Validation failed", errors);
    }

    // Обработка кастомных бизнес-ошибок
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessValidationException.class)
    public ErrorResponse handleBusinessException(BusinessValidationException ex) {
        log.warn("Business exception: {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage()) {

            @ResponseStatus(HttpStatus.NOT_FOUND)
            @ExceptionHandler(ResourceNotFoundException.class)
            public ErrorResponse handleResourceNotFound(ResourceNotFoundException ex) {
                log.warn("Resource not found: {}", ex.getMessage());
                return new ErrorResponse(ex.getMessage()) {

                };
            }

            // Все остальные исключения
            @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            @ExceptionHandler(Exception.class)
            public ErrorResponse handleAllExceptions(Exception ex) {
                log.error("Internal error: ", ex);
                return new ErrorResponse("Internal server error") {
                };
            }
        };
    }
}

