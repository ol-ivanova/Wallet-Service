package org.example.wallet_service.factory;

import org.example.wallet_service.service.PlayerAccountService;

public class PlayerAccountServiceFactory {
    private static PlayerAccountService playerAccountService;

    private PlayerAccountServiceFactory(){}

    public static PlayerAccountService getPlayerAccountService(){
        if(playerAccountService == null){
            playerAccountService = new PlayerAccountService();
        }
        return playerAccountService;
    }
}
