package org.example.wallet_service.repository;

import org.example.wallet_service.domain.dto.CreatePlayerAccountDto;
import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.factory.PlayerRepositoryFactory;
import org.example.wallet_service.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class PlayerAccountRepository {
    PlayerRepository playerRepository;
    public PlayerAccountRepository(){
        playerRepository = PlayerRepositoryFactory.getPlayerRepository();
    }
    public static final String PLAYER_ACCOUNT_INSERT_VALUES = """
            INSERT INTO wallet_schema.player_account (player_id, balance) VALUES (?,?);
            """;
    public static final String PLAYER_ACCOUNT_SELECT = """
            SELECT * FROM wallet_schema.player_account WHERE account_number = ?;
            """;
    public static final String PLAYER_ACCOUNT_SELECT_BY_ID_PLAYER = """
            SELECT * FROM wallet_schema.player_account WHERE player_id = ?;
            """;
    public static final String UPDATE_BALANCE_BY_ACCOUNT_NUMBER = """
            UPDATE wallet_schema.player_account SET balance = ? WHERE account_number = ?;
            """;
    public Optional<PlayerAccount> save(CreatePlayerAccountDto createPlayerAccountDto){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_ACCOUNT_INSERT_VALUES, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, createPlayerAccountDto.getPlayerId());
            preparedStatement.setObject(2, createPlayerAccountDto.getBalance());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            PlayerAccount playerAccount = null;
            if(generatedKeys.next()){
                playerAccount = PlayerAccount.builder()
                        .accountNumber(generatedKeys.getObject("account_number", Long.class))
                        .build();
            }
            return Optional.ofNullable(playerAccount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<PlayerAccount> findAccountByNumber(long accountNumber) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_ACCOUNT_SELECT)) {
            preparedStatement.setObject(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            PlayerAccount playerAccount = null;
            if (resultSet.next()) {
                playerAccount = PlayerAccount.builder()
                        .accountNumber(resultSet.getObject("account_number", Long.class))
                        .balance(resultSet.getObject("balance", BigDecimal.class))
                        .player(playerRepository.findPlayerById(
                                        resultSet.getObject("player_id", Integer.class))
                                .orElse(null))
                        .build();
            }
            return Optional.ofNullable(playerAccount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<PlayerAccount> findAccountByPlayerId(int playerId) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(PLAYER_ACCOUNT_SELECT_BY_ID_PLAYER)) {
            preparedStatement.setObject(1, playerId );
            ResultSet resultSet = preparedStatement.executeQuery();
            PlayerAccount playerAccount = null;
            if (resultSet.next()) {
                playerAccount = PlayerAccount.builder()
                        .accountNumber(resultSet.getObject("account_number", Long.class))
                        .balance(resultSet.getObject("balance", BigDecimal.class))
                        .player(playerRepository.findPlayerById(
                                        resultSet.getObject("player_id", Integer.class))
                                .orElse(null))
                        .build();
            }
            return Optional.ofNullable(playerAccount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBalanceByAccountNumber(BigDecimal balance, long accountNumber) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BALANCE_BY_ACCOUNT_NUMBER)) {
            preparedStatement.setObject(1, balance);
            preparedStatement.setObject(2, accountNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
