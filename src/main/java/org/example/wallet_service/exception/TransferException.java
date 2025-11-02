package org.example.wallet_service.exception;

public class TransferException extends RuntimeException {
    public TransferException(String message) {
        super(message);
    }
}
