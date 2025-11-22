package org.example.wallet_service.domain.mapper;

import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.entity.Player;

/**
 * Класс-маппер для Player
 */
public class CreatePlayerMapper implements Mapper<CreatePlayerDto, Player>{

    /**
     * Реализация singleton класса-маппера
     */
    private static final CreatePlayerMapper PLAYER_MAPPER = new CreatePlayerMapper();
    public static CreatePlayerMapper getInstance() {
        return PLAYER_MAPPER;
    }

    /**
     * метод, сопоставляющий dto объект CreatePlayerDto и сущность Player
     * @param createPlayerDto - dto объект
     * @return - соответствующая сущность
     */
    @Override
    public Player map(CreatePlayerDto createPlayerDto) {
        return Player.builder()
                .name(createPlayerDto.getName())
                .username(createPlayerDto.getUsername())
                .password(createPlayerDto.getPassword())
                .build();
    }
}
