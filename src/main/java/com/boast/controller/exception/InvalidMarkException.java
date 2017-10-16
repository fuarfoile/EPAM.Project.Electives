package com.boast.controller.exception;

public class InvalidMarkException extends Exception {
    public InvalidMarkException() {}

    public InvalidMarkException(String message) {
        super (message);
    }

    public InvalidMarkException(Throwable cause) {
        super (cause);
    }

    public InvalidMarkException(String message, Throwable cause) {
        super (message, cause);
    }
}
