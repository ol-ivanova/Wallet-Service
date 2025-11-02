package org.example.wallet_service.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreatePlayerAccountDto {
    private BigDecimal balance;
    private int playerId;
}
