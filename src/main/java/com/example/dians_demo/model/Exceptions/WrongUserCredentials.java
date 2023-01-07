package com.example.dians_demo.model.Exceptions;

public class WrongUserCredentials extends RuntimeException{
    public WrongUserCredentials(String message) {
        super(message);
    }
}
