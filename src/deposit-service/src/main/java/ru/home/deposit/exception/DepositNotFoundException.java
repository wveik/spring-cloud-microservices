package ru.home.deposit.exception;

public class DepositNotFoundException extends RuntimeException {
    public DepositNotFoundException(String message) {
        super(message);
    }
}

