package org.example.wallet_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.wallet_service.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @AllArgsConstructor - генерирует контсрутор для всех аргумеетов\полей
 * @NoArgsConstructor - генерирует дефолтный конструтор
 * @Data - генерирует для всех полей  @ToString, @EqualsAndHashCode, @Getter/@Setter и @RequiredArgsConstructor
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

/**
 * Сущность - транзакции
 */
public class Transaction {
    /**
     * id, генерируемый при вставке экземпляра класса в БД
     */
    private UUID id;
    /**
     * Время транзакции, генерируемое при вставке экземпляра класса в БД
     */
    private LocalDateTime createdDate;
    /**
     * Тип транзакции
     */
    private TransactionType type;
    /**
     * Сумма транзакции
     */
    private BigDecimal sum;
    /**
     * Счет отправителя
     */
    private long playerAccountFrom;
    /**
     * Счет получателя
     */
    private long playerAccountTo;
}
