package org.example.wallet_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

/**
 * Сущность - счет пользователя
 */
public class PlayerAccount {
    /**
     * Номер счета, генерируемый при вставке экземпляра класса в БД
     */
    private Long accountNumber;
    /**
     * Баланс счета
     */
    private BigDecimal balance;
    /**
     * id пользователя, к которому привязан счет
     */
    private int playerId;
}
