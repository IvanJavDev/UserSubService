package org.example.usersubscriptionservice.controller.advice;

import org.example.usersubscriptionservice.controller.UserController;
import org.example.usersubscriptionservice.exceptions.SubscriptionConflictException;
import org.example.usersubscriptionservice.exceptions.UserNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice {

    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<Response> handleUserNotFound(UserNotFoundExeption ex) {
        String message = String.format("%s %s", LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(new Response(message), HttpStatus.NOT_FOUND);
    }
}
