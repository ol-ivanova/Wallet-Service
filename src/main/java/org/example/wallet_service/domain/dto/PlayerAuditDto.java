package org.example.wallet_service.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.example.wallet_service.domain.enums.AuditAction;

@Data
@Builder
public class PlayerAuditDto {
    private AuditAction auditAction;
    private int playerId;
}
