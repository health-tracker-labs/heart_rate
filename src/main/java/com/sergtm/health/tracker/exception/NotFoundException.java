package com.sergtm.health.tracker.exception;

public abstract class NotFoundException extends RuntimeException {
    NotFoundException() {
        super();
    }

    NotFoundException(String message) {
        super(message);
    }
}
