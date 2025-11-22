package org.example.wallet_service.domain.mapper;

import org.example.wallet_service.domain.dto.PlayerAuditDto;
import org.example.wallet_service.domain.entity.PlayerAudit;

/**
 * Класс-маппер для PlayerAudit
 */
public class PlayerAuditMapper implements Mapper<PlayerAuditDto, PlayerAudit>{

    /**
     * Реализация singleton класса-маппера
     */
    private static final PlayerAuditMapper PLAYER_AUDIT_MAPPER = new PlayerAuditMapper();
    public static PlayerAuditMapper getInstance() {
        return PLAYER_AUDIT_MAPPER;
    }

    /**
     * метод, сопоставляющий dto объект PlayerAuditDto и сущность PlayerAudit
     * @param playerAuditDto - dto объект
     * @return - соответствующая сущность
     */
    @Override
    public PlayerAudit map(PlayerAuditDto playerAuditDto) {
        return PlayerAudit.builder()
                .playerId(playerAuditDto.getPlayerId())
                .action(playerAuditDto.getAuditAction())
                .build();
    }
}
