package org.example.wallet_service.repository;

import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.dto.TransactionDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.domain.entity.Transaction;
import org.example.wallet_service.domain.enums.TransactionType;
import org.example.wallet_service.factory.PlayerAccountRepositoryFactory;
import org.example.wallet_service.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionRepository {
    PlayerAccountRepository playerAccountRepository;
    public TransactionRepository(){
        playerAccountRepository = PlayerAccountRepositoryFactory.getPlayerAccountRepository();
    }
    public static final String TRANSACTION_INSERT_VALUES = """
            INSERT INTO wallet_schema.transaction (type, sum, player_account_from, player_account_to) VALUES (?, ?, ?, ?);
            """;

    public static final String TRANSACTION_SELECT_BY_ACCOUNT_NUMBER = """
            SELECT * FROM wallet_schema.transaction WHERE player_account_from = ? OR player_account_to = ?;
            """;

    public Optional<Transaction> insert(TransactionDto transactionDto){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(TRANSACTION_INSERT_VALUES, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, transactionDto.getType().name());
            preparedStatement.setObject(2, transactionDto.getSum());
            preparedStatement.setObject(3, transactionDto.getPlayerAccountFrom());
            preparedStatement.setObject(4, transactionDto.getPlayerAccountTo());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Transaction transaction = null;
            if(generatedKeys.next()){
                transaction = Transaction.builder()
                        .id(generatedKeys.getObject("id", UUID.class))
                        .build();
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> transactionHistoryByAccountNumber(Long numberAccount){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(TRANSACTION_SELECT_BY_ACCOUNT_NUMBER)){
            preparedStatement.setObject(1, numberAccount);
            preparedStatement.setObject(2, numberAccount);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Transaction> transactionList = new ArrayList<>();
            while(resultSet.next()){
                Transaction transaction = Transaction.builder()
                        .id(resultSet.getObject("id", UUID.class))
                        .createdDate(resultSet.getObject("created_date", LocalDateTime.class))
                        .type(TransactionType.valueOf(resultSet.getString("type")))
                        .sum(resultSet.getObject("sum", BigDecimal.class))
                        .playerAccountFrom(playerAccountRepository.findAccountByNumber(
                                resultSet.getObject("player_account_from", Long.class))
                                .orElse(null))
                        .playerAccountTo(playerAccountRepository.findAccountByNumber(
                                resultSet.getObject("player_account_to", Long.class))
                                .orElse(null))
                        .build();

                transactionList.add(transaction);
            }
            return transactionList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
