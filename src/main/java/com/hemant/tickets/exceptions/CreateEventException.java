package com.hemant.tickets.exceptions;

public class CreateEventException extends RuntimeException{

    public CreateEventException() {
    }

    public CreateEventException(String message) {
        super(message);
    }

    public CreateEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateEventException(Throwable cause) {
        super(cause);
    }

    public CreateEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
