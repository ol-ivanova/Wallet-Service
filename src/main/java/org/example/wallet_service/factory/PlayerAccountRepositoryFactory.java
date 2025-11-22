package org.example.wallet_service.factory;

import org.example.wallet_service.repository.PlayerAccountRepository;

/**
 * Данный класс отвечает за создание единственного экземпляра класса PlayerAccountRepository
 */
public class PlayerAccountRepositoryFactory {
    private static PlayerAccountRepository playerAccountRepository;

    private PlayerAccountRepositoryFactory(){}

    public static PlayerAccountRepository getPlayerAccountRepository(){
        if (playerAccountRepository == null){
            playerAccountRepository = new PlayerAccountRepository();
        }
        return playerAccountRepository;
    }
}
