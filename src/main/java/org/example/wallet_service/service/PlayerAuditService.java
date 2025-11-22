package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.PlayerAuditDto;
import org.example.wallet_service.domain.entity.PlayerAudit;
import org.example.wallet_service.domain.mapper.PlayerAccountMapper;
import org.example.wallet_service.domain.mapper.PlayerAuditMapper;
import org.example.wallet_service.factory.PlayerAuditRepositoryFactory;
import org.example.wallet_service.repository.PlayerAuditRepository;

import java.util.List;

public class PlayerAuditService {
    private PlayerAuditRepository playerAuditRepository;

    private final PlayerAuditMapper playerAuditMapper;

    public PlayerAuditService(){
        playerAuditRepository = PlayerAuditRepositoryFactory.getplayerAuditRepository();
        playerAuditMapper = PlayerAuditMapper.getInstance();
    }

    /**
     * Метод для создания логов
     * @param playerAuditDto - dto объект
     */

    public void createLog(PlayerAuditDto playerAuditDto){
        PlayerAudit playerAudit = playerAuditMapper.map(playerAuditDto);
        playerAuditRepository.save(playerAudit);
    }

    /**
     * Метод для получения логов по id
     * @param playerId - id пользователя
     * @return - Список логов
     */
    public List<PlayerAudit> getLogsByPlayerId(int playerId){
        return playerAuditRepository.findLogsByPlayerId(playerId);
    }
}
