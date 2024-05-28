package com.service.bank.exception;

public class InvalidRegistrationDataException extends RuntimeException{
    public InvalidRegistrationDataException(String message) {
        super(message);
    }
}
