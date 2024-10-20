package com.hasanalmunawr.book_network.exception.custom;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
    public UserAlreadyExistException() {
        super("User already exists");
    }
}
