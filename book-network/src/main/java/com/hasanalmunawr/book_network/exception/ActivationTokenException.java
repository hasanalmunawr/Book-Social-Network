package com.hasanalmunawr.book_network.exception;

public class ActivationTokenException extends RuntimeException {

    public ActivationTokenException(String message) {
        super(message);
    }

    public ActivationTokenException() {
        super("Activation token expired");
    }
}
