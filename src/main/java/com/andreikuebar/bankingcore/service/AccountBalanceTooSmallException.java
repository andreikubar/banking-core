package com.andreikuebar.bankingcore.service;

public class AccountBalanceTooSmallException extends RuntimeException{
    public AccountBalanceTooSmallException(String message) {
        super(message);
    }
}
