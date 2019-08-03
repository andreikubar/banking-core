package com.andreikuebar.bankingdemo.service;

public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
