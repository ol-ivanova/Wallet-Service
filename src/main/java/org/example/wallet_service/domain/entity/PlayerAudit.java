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

/**
 * Сущность - Логи пользователя
 */
public class PlayerAudit {
    /**
     * id лога, генерируемый при вставке экземпляра класса в БД
     */
    private Integer id;
    /**
     * Дата и время, генерируемое при вставке экземпляра класса в БД
     */
    private LocalDateTime dateTime;
    /**
     * Тип активности
     */
    private AuditAction action;
    /**
     * id пользователя, к которому привязана активность
     */
    private Integer playerId;
}
