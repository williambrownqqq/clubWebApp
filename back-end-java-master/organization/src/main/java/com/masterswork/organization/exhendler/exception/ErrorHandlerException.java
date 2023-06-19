package com.masterswork.organization.exhendler.exception;

public class ErrorHandlerException extends Exception {

    public ErrorHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorHandlerException(String message) {
        super(message);
    }
}
