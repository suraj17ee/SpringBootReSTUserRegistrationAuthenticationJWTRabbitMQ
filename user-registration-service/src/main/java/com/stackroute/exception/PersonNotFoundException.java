package com.stackroute.exception;

public class PersonNotFoundException extends RuntimeException{

    public static final long serialVersionUID= 1L;

    public PersonNotFoundException() {
        super();
    }

    public PersonNotFoundException(String message){
        super(message);
    }
}
