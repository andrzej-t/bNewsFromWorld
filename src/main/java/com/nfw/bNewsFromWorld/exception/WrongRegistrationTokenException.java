package com.nfw.bNewsFromWorld.exception;

public class WrongRegistrationTokenException extends RuntimeException {
    public WrongRegistrationTokenException(String message) {
        super(message);
    }
}
