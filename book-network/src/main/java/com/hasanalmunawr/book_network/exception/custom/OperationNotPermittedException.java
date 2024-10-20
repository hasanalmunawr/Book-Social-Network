package com.hasanalmunawr.book_network.exception.custom;

public class OperationNotPermittedException extends RuntimeException {

    public OperationNotPermittedException() {
        super("Operation not permitted");
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }
}
