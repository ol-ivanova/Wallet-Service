package org.example.wallet_service.domain.enums;

/**
 * Перечисление - тип транзакции
 */
public enum TransactionType {
    /**
     * Дебит - пополнение/получение денежных средств
     */
    DEBIT,
    /**
     * Кредит - перевод денежных средств
     */
    CREDIT;
}
