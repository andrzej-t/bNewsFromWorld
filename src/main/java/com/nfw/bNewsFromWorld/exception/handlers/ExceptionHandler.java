package com.nfw.bNewsFromWorld.exception.handlers;

import com.nfw.bNewsFromWorld.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> emptyFieldException(EmptyFieldException e) {
        String message = "Fields can not be empty";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> userExistsException(UserExistsException e) {
        String message = "User with this email already exists";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> usageLimitReachedException(UsageLimitReachedException e) {
        String message = "Application usage limit for this day has ended. Try tomorrow.";
        return new ResponseEntity<>(message, HttpStatus.PAYMENT_REQUIRED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> wrongEmailException(WrongEmailException e) {
        String message = "Wrong email address.";
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> emailAlreadyConfirmedException(EmailAlreadyConfirmedException e) {
        String message = "Your email address was already confirmed";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> registrationTokenExpiredException(RegistrationTokenExpiredException e) {
        String message = "Your registration token has expired. Try to register again.";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> wrongRegistrationTokenException(WrongRegistrationTokenException e) {
        String message = "Token not found.";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> invalidCharactersException(InvalidCharactersException e) {
        String message = "Invalid characters in field. You should use only up to 30 signs: capital or small letters, numbers and signs: ! - @ _";
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> wrongCredentialsException(WrongCredentialsException e) {
        String message = "Wrong credentials.";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
