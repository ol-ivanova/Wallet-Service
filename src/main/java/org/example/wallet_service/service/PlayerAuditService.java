package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.PlayerAuditDto;
import org.example.wallet_service.domain.entity.PlayerAudit;
import org.example.wallet_service.factory.PlayerAuditRepositoryFactory;
import org.example.wallet_service.repository.PlayerAuditRepository;

import java.util.List;

public class PlayerAuditService {
    private PlayerAuditRepository playerAuditRepository;

    public PlayerAuditService(){
        playerAuditRepository = PlayerAuditRepositoryFactory.getplayerAuditRepository();
    }

    public void createLog(PlayerAuditDto playerAuditDto){
        playerAuditRepository.insertLog(playerAuditDto);
    }

    public List<PlayerAudit> getLogsByPlayerId(int playerId){
        return playerAuditRepository.findLogsByPlayerId(playerId);
    }
}
