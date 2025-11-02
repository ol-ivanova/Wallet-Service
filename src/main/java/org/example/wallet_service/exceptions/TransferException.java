package org.example.wallet_service.exceptions;

public class TransferException extends RuntimeException {
    public TransferException(String message) {
        super(message);
    }
}
