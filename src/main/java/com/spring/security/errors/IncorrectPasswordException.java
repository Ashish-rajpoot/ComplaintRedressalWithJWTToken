package com.spring.security.errors;

public class IncorrectPasswordException extends Exception{

    public IncorrectPasswordException (String message){
        super(message);
    }
}
