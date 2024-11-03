package com.hasanalmunawr.book_network.exception.custom;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
    public UserExistException() {
        super("User already exists");
    }
}
