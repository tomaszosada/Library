package com.example.library.exception;

public class NoBookException extends RuntimeException{
    public NoBookException(String message){
        super(message);
    }
}
