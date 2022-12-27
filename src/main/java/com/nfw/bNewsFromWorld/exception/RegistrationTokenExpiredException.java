package com.nfw.bNewsFromWorld.exception;

public class RegistrationTokenExpiredException extends RuntimeException {
    public RegistrationTokenExpiredException(String message) {
        super(message);
    }
}
