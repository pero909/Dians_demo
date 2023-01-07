package com.example.dians_demo.model.Exceptions;

public class PasswordDontMatchException extends RuntimeException{
    public PasswordDontMatchException(String message) {
        super(message);
    }
}
