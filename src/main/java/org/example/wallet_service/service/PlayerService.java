package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.AuthorizationDto;
import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.mapper.AuthorizationMapper;
import org.example.wallet_service.domain.mapper.CreatePlayerMapper;
import org.example.wallet_service.exception.PlayerException;
import org.example.wallet_service.factory.PlayerRepositoryFactory;
import org.example.wallet_service.repository.PlayerRepository;

public class PlayerService {
    private PlayerRepository playerRepository;

    private final CreatePlayerMapper createPlayerMapper;

    private final AuthorizationMapper authorizationMapper;

    public PlayerService(){
        playerRepository = PlayerRepositoryFactory.getPlayerRepository();
        createPlayerMapper = CreatePlayerMapper.getInstance();
        authorizationMapper = AuthorizationMapper.getInstance();
    }

    /**
     * Метод для создания пользователя с проверкой уникальности логина
     * @param createPlayerDto - dto объект
     * @return - объект Player со сгенерированным id
     */
    public Player createPlayer(CreatePlayerDto createPlayerDto){
        Player player = createPlayerMapper.map(createPlayerDto);
        if (playerRepository.findByUsername(player.getUsername()).isPresent()){
            throw new PlayerException("Такой логин уже используется");
        }
        return playerRepository.save(player)
                .orElseThrow(() -> new PlayerException("Пользователь не создан"));
    }

    /**
     * Метод для поиска аккаунта по учетным данным
     * @param username - логин пользователя
     * @param password - пароль пользователя
     * @return - объект Player
     */
    public Player findByCredentials(String username, String password){
        return playerRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new PlayerException("Пользователь не найден"));
    }
}
