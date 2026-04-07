package com.sergtm.exception;

public class HeartRateNotFoundException extends RuntimeException {
    public HeartRateNotFoundException() {
        super();
    }

    public HeartRateNotFoundException(String message) {
        super(message);
    }
}
