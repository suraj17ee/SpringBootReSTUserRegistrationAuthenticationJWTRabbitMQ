package com.stackroute.exception;

public class UserFileNotFoundException extends RuntimeException{
    public static final long serialVersionUID= 1L;

    public UserFileNotFoundException() {
        super();
    }

    public UserFileNotFoundException(String message) {
        super(message);
    }
}
