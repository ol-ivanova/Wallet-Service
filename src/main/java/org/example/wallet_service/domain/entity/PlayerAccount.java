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
public class PlayerAccount {
    private Long accountNumber;
    private BigDecimal balance;
    private Player player;
}
