package com.sergtm.health.tracker.rest.handler;

import com.sergtm.health.tracker.exception.NotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sergtm.health.tracker.rest.handler.GlobalRestExceptionHandler.REST_CONTROLLERS_PACKAGE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice(basePackages = REST_CONTROLLERS_PACKAGE)
public class GlobalRestExceptionHandler {
    static final String REST_CONTROLLERS_PACKAGE = "com.sergtm.health.tracker.rest.controller";

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        return ProblemDetail
                .forStatusAndDetail(NOT_FOUND, ex.getMessage());
    }
}
