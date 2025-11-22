package org.example.wallet_service.exception;

/**
 * Exception, выбрасывающийся при ошибке занесения информации о транзакции в БД или если сумма перевода превышает баланс
 */
public class TransferException extends RuntimeException {
    public TransferException(String message) {
        super(message);
    }
}
