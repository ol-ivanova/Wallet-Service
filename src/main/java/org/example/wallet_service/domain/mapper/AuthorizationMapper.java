package org.example.wallet_service.domain.mapper;

import org.example.wallet_service.domain.dto.AuthorizationDto;
import org.example.wallet_service.domain.entity.Player;

/**
 * Класс-маппер для Player
 */
public class AuthorizationMapper implements Mapper<AuthorizationDto, Player> {

    /**
     * Реализация singleton класса-маппера
     */
    private static final AuthorizationMapper AUTHORIZATION_MAPPER = new AuthorizationMapper();
    public static AuthorizationMapper getInstance(){
        return AUTHORIZATION_MAPPER;
    }

    /**
     * метод, сопоставляющий dto объект AuthorizationDto и сущность Player
     * @param authorizationDto - dto объект
     * @return - соответствующая сущность
     */
    @Override
    public Player map(AuthorizationDto authorizationDto){
        return Player.builder()
                .username(authorizationDto.getUsername())
                .password(authorizationDto.getPassword())
                .build();
    }
}
