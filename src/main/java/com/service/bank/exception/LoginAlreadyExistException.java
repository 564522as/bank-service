package com.service.bank.exception;

public class LoginAlreadyExistException extends RuntimeException{
    public LoginAlreadyExistException(String message) {
        super(message);
    }
}
