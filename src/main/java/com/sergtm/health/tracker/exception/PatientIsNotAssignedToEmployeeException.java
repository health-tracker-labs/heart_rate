package com.sergtm.health.tracker.exception;

public class PatientIsNotAssignedToEmployeeException extends NotFoundException {
    public PatientIsNotAssignedToEmployeeException(String message) {
        super(message);
    }
}
