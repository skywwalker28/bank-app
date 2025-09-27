package com.skyww28.bank.Exception;

public class InvalidTransferException extends RuntimeException{
    public InvalidTransferException(String message) {
        super(message);
    }
}
