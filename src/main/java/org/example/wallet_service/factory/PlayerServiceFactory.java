package org.example.wallet_service.factory;

import org.example.wallet_service.service.PlayerService;

/**
 * Данный класс отвечает за создание единственного экземпляра класса PlayerService
 */
public class PlayerServiceFactory {
    private static PlayerService playerService;

    private PlayerServiceFactory(){};

    public static PlayerService getPlayerService(){
        if (playerService == null){
            playerService = new PlayerService();
        }
        return playerService;
    }
}
