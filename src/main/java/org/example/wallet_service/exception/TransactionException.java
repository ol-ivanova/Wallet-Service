package org.example.wallet_service.exception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
