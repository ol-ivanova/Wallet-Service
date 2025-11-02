package org.example.wallet_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.wallet_service.domain.enums.AuditAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayerAudit {
    private Integer id;
    private LocalDateTime dateTime;
    private AuditAction action;
    private Integer playerId;
}
