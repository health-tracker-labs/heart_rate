package com.sergtm.health.tracker.exception;

public class PatientNotFoundException extends NotFoundException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
