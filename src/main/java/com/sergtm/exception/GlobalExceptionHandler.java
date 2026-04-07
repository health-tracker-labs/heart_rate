package com.sergtm.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_PAGE = "error";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "error";

    @ExceptionHandler({HeartRateNotFoundException.class, PersonNotFoundException.class})
    public String handle(RuntimeException ex, Model model) {
        model.addAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, ex.getMessage());
        return ERROR_PAGE;
    }
}
