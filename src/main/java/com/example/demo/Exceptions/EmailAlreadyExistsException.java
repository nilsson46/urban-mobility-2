package com.example.demo.Exceptions;

public class EmailAlreadyExistsException extends Throwable {
    public EmailAlreadyExistsException(String message) {
            super(message);
    }
}
