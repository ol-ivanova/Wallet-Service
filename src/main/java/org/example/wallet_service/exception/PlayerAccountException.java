package org.example.wallet_service.exception;

/**
 * Exception, если аккаунт не создан\не найден
 */
public class PlayerAccountException extends RuntimeException {
    public PlayerAccountException(String message) {
        super(message);
    }
}
