package com.flem.resthello.exception;

public class UsernameNotFoundException extends Exception {

    private static final long serialVersionUID = 3L;

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
