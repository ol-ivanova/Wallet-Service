package org.example.wallet_service.factory;

import org.example.wallet_service.repository.PlayerAuditRepository;

/**
 * Данный класс отвечает за создание единственного экземпляра класса PlayerAuditRepository
 */
public class PlayerAuditRepositoryFactory {
    private static PlayerAuditRepository playerAuditRepository;

    private PlayerAuditRepositoryFactory(){};

    public static PlayerAuditRepository getplayerAuditRepository(){
        if (playerAuditRepository == null){
            playerAuditRepository = new PlayerAuditRepository();
        }
        return playerAuditRepository;
    }
}
