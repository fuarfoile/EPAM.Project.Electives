package com.boast.controller.exception;

public class MarkException extends Exception {
    public MarkException() {}

    public MarkException(String message) {
        super (message);
    }

    public MarkException(Throwable cause) {
        super (cause);
    }

    public MarkException(String message, Throwable cause) {
        super (message, cause);
    }
}
