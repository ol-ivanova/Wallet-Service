package org.example.wallet_service.domain.mapper;

import org.example.wallet_service.domain.dto.CreatePlayerAccountDto;
import org.example.wallet_service.domain.entity.PlayerAccount;

/**
 * Класс-маппер для PlayerAccount
 */
public class PlayerAccountMapper implements Mapper<CreatePlayerAccountDto, PlayerAccount>{

    /**
     * Реализация singleton класса-маппера
     */
    private static final PlayerAccountMapper PLAYER_ACCOUNT_MAPPER = new PlayerAccountMapper();
    public static PlayerAccountMapper getInstance() {
        return PLAYER_ACCOUNT_MAPPER;
    }

    /**
     * метод, сопоставляющий dto объект CreatePlayerAccountDto и сущность PlayerAccount
     * @param createPlayerAccountDto - dto объект
     * @return - соответствующая сущность
     */
    @Override
    public PlayerAccount map(CreatePlayerAccountDto createPlayerAccountDto) {
        return PlayerAccount.builder()
                .playerId(createPlayerAccountDto.getPlayerId())
                .balance(createPlayerAccountDto.getBalance())
                .build();
    }
}
