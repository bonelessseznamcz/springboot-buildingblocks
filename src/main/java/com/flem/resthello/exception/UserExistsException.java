package com.flem.resthello.exception;

public class UserExistsException extends Exception {

    private static final long serialVersionUID = 2L;

    public UserExistsException(String message) {
        super(message);
    }
}
