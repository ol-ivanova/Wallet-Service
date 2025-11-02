package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.AuthorizationDto;
import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.exceptions.PlayerException;
import org.example.wallet_service.factory.PlayerRepositoryFactory;
import org.example.wallet_service.repository.PlayerRepository;

import java.util.Optional;

public class PlayerService {
    private PlayerRepository playerRepository;
    public PlayerService(){
        playerRepository = PlayerRepositoryFactory.getPlayerRepository();
    }

    public Player createPlayer(CreatePlayerDto createPlayerDto){
        return playerRepository.create(createPlayerDto)
                .orElseThrow(() -> new PlayerException("Пользователь не создан"));
    }

    public Player findByCreds(AuthorizationDto authorizationDto){
        return playerRepository.find(authorizationDto)
                .orElseThrow(() -> new PlayerException("Пользователь не найден"));
    }
}
