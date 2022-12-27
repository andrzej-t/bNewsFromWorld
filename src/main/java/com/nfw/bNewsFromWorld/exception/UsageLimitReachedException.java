package com.nfw.bNewsFromWorld.exception;

public class UsageLimitReachedException extends RuntimeException {
    public UsageLimitReachedException(String message) {
        super(message);
    }
}
