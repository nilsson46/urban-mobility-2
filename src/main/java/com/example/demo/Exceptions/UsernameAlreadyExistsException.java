package com.example.demo.Exceptions;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
