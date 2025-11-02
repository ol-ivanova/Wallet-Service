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
public class Transaction {
    private UUID id;
    private LocalDateTime createdDate;
    private TransactionType type;
    private BigDecimal sum;
    private PlayerAccount playerAccountFrom;
    private PlayerAccount playerAccountTo;
}
