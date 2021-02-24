package ru.fitness.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.fitness.dto.DError;
import ru.fitness.exception.LogicException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(value = LogicException.class)
    public DError handleLogicException(LogicException ex, HttpServletResponse response) {
        logger.info("Error", ex);
        HttpStatus status = null;
        switch (ex.getCode()) {
            case NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case OTHER:
                status = HttpStatus.BAD_REQUEST;
                break;
        }
        response.setStatus(status.value());
        return new DError(ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DError handleUnexpectedException(RuntimeException ex) {
        logger.error("Unexpected error", ex);
        return new DError(ex.getMessage());
    }
}
