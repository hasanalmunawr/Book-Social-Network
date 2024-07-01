package com.hasanalmunawr.book_network.exception;

public class OperationNotPermittedException extends RuntimeException {

    public OperationNotPermittedException() {
        super("Operation not permitted");
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }
}
