package com.sergtm.health.tracker.exception.handler;

import com.sergtm.health.tracker.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.sergtm.health.tracker.exception.handler.GlobalExceptionHandler.MVC_CONTROLLERS_PACKAGE;

@ControllerAdvice(basePackages = MVC_CONTROLLERS_PACKAGE)
public class GlobalExceptionHandler {
    static final String MVC_CONTROLLERS_PACKAGE = "com.sergtm.controllers";

    private static final String ERROR_PAGE = "error";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "error";

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
        model.addAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, ex.getMessage());
        return ERROR_PAGE;
    }
}
