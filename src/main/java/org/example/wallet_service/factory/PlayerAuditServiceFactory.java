package org.example.wallet_service.factory;

import org.example.wallet_service.service.PlayerAuditService;

public class PlayerAuditServiceFactory {
    private static PlayerAuditService playerAuditService;

    private PlayerAuditServiceFactory(){};

    public static PlayerAuditService getPlayerAuditService(){
        if (playerAuditService == null){
            playerAuditService = new PlayerAuditService();
        }
        return playerAuditService;
    }
}
