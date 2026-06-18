package com.sergtm.health.tracker.rest.handler;

import com.sergtm.health.tracker.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sergtm.health.tracker.rest.handler.GlobalRestExceptionHandler.REST_CONTROLLERS_PACKAGE;

@RestControllerAdvice(basePackages = REST_CONTROLLERS_PACKAGE)
public class GlobalRestExceptionHandler {
    static final String REST_CONTROLLERS_PACKAGE = "com.sergtm.health.tracker.rest.controller";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
