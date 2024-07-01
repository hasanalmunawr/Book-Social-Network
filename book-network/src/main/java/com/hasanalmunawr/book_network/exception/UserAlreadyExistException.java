package com.hasanalmunawr.book_network.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
    public UserAlreadyExistException() {
        super("User already exists");
    }
}
