package org.example.wallet_service.factory;

import org.example.wallet_service.repository.PlayerRepository;

public class PlayerRepositoryFactory {
    private static PlayerRepository playerRepository;

    private PlayerRepositoryFactory(){}

    public static PlayerRepository getPlayerRepository(){
        if(playerRepository == null){
            playerRepository = new PlayerRepository();
        }
        return playerRepository;
    }
}
