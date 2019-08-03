package com.andreikuebar.bankingcore.service;

public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
