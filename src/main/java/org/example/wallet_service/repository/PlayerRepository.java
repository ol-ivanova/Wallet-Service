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
    public static final String PLAYER_SELECT_BY_USERNAME = """
            SELECT * FROM wallet_schema.player WHERE username = ?;
            """;

    /**
     * Метод, вставляющий объект Player в БД
     * @param player - объект Player
     * @return - объект Player со сгенерированным id
     */
    public Optional<Player> save(Player player){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_INSERT_VALUES,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, player.getName());
            preparedStatement.setObject(2,player.getUsername());
            preparedStatement.setObject(3,player.getPassword());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                player.setId(generatedKeys.getObject("id", Integer.class));
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для авторизации, запрашивающий пользователя с переданным логином и паролем в БД
     * @param username - логин пользователя
     * @param password - пароль пользователя
     * @return - объект Player
     */
    public Optional<Player> findByUsernameAndPassword(String username, String password){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_SELECT)) {
            preparedStatement.setObject(1, username);
            preparedStatement.setObject(2, password);
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

    /**
     * Метод, запрашивающий пользователя с переданным логином в БД
     * @param username - логин пользователя
     * @return - объект Player
     */
    public Optional<Player> findByUsername(String username){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_SELECT_BY_USERNAME)) {
            preparedStatement.setObject(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            Player player = null;
            if(resultSet.next()){
                player = Player.builder()
                        .username(resultSet.getObject("username", String.class))
                        .build();
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
