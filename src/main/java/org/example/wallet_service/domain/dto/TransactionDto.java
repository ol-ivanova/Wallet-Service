package org.example.wallet_service.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.enums.TransactionType;

import java.math.BigDecimal;

@Data
@Builder

/**
 * dto объект для сущности Transaction
 */
public class TransactionDto {
    private TransactionType type;
    private BigDecimal sum;
    private Long playerAccountFrom;
    private Long playerAccountTo;
}
