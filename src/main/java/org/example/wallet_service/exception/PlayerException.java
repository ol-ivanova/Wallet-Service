package org.example.wallet_service.exception;

/**
 * Exception, выбрасывающийся, если логин уже используется или пользователь не найден/не создан
 */
public class PlayerException extends RuntimeException {
    public PlayerException(String message) {
        super(message);
    }
}
