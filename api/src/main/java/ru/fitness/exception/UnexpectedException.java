package ru.fitness.exception;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }

    public UnexpectedException(String message) {
        super(message);
    }
}
