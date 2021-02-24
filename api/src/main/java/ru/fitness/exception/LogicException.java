package ru.fitness.exception;

public class LogicException extends RuntimeException {
    public enum ErrorCode {
        NOT_FOUND,
        OTHER
    }

    private final ErrorCode code;

    public ErrorCode getCode() {
        return code;
    }

    public LogicException(String message) {
        this(message, ErrorCode.OTHER);
    }

    public LogicException(String message, Throwable cause) {
        this(message, cause, ErrorCode.OTHER);
    }

    public LogicException(Throwable cause) {
        this(cause, ErrorCode.OTHER);
    }

    public LogicException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public LogicException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public LogicException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }
}
