package com.andreikuebar.bankingdemo.service;

public class AccountBalanceTooSmallException extends RuntimeException{
    public AccountBalanceTooSmallException(String message) {
        super(message);
    }
}
