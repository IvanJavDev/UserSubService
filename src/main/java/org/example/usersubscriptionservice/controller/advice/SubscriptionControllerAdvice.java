package org.example.usersubscriptionservice.controller.advice;

import org.example.usersubscriptionservice.controller.SubscriptionController;
import org.example.usersubscriptionservice.exceptions.BusinessValidationException;
import org.example.usersubscriptionservice.exceptions.SubscriptionConflictException;
import org.example.usersubscriptionservice.exceptions.UserNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice(assignableTypes = SubscriptionController.class)
public class SubscriptionControllerAdvice {

    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<Response> handleUserNotFound(UserNotFoundExeption ex) {
        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(new Response(message), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SubscriptionConflictException.class)
    public ResponseEntity<Response> HandleSubscriptionConflict(SubscriptionConflictException ex) {
        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(new Response(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<Response> HandleBusinessValidation (BusinessValidationException ex) {
        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(new Response(message), HttpStatus.BAD_REQUEST);
    }
}
