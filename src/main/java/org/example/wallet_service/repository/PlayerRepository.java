package org.example.wallet_service.repository;

import org.example.wallet_service.domain.dto.AuthorizationDto;
import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.util.ConnectionManager;

import java.sql.*;
import java.util.Optional;

public class PlayerRepository {
    public static final String PLAYER_INSERT_VALUES = """
            INSERT INTO wallet_schema.player (name, username, password) VALUES (?, ?, ?);
            """;
    public static final String PLAYER_SELECT = """
            SELECT * FROM wallet_schema.player WHERE username = ? AND password = ?;
            """;
    public static final String PLAYER_SELECT_BY_ID = """
            SELECT * FROM wallet_schema.player WHERE id = ?;
            """;
    public Optional<Player> create(CreatePlayerDto createPlayerDto){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_INSERT_VALUES,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, createPlayerDto.getName());
            preparedStatement.setObject(2,createPlayerDto.getUsername());
            preparedStatement.setObject(3,createPlayerDto.getPassword());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Player player = null;
            if(generatedKeys.next()){
                player = Player.builder()
                        .id(generatedKeys.getObject("id", Integer.class))
                        .name(createPlayerDto.getName())
                        .username(createPlayerDto.getUsername())
                        .password(createPlayerDto.getPassword())
                        .build();
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Player> find(AuthorizationDto authorizationDto){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_SELECT)) {
            preparedStatement.setObject(1, authorizationDto.getUsername());
            preparedStatement.setObject(2, authorizationDto.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            Player player = null;
            if(resultSet.next()){
                player = Player.builder()
                        .id(resultSet.getObject("id", Integer.class))
                        .name(resultSet.getObject("name", String.class))
                        .username(resultSet.getObject("username", String.class))
                        .password(resultSet.getObject("password", String.class))
                        .build();
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Player> findPlayerById(Integer id){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_SELECT_BY_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Player player = null;
            if(resultSet.next()){
                player = Player.builder()
                        .id(resultSet.getObject("id", Integer.class))
                        .name(resultSet.getObject("name", String.class))
                        .username(resultSet.getObject("username", String.class))
                        .password(resultSet.getObject("password", String.class))
                        .build();
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
