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

    /**
     * Метод, вставляющий объект Transaction в БД
     * @param transaction - объект Transaction
     * @return - объект Transaction со сгенерированным id
     */
    public Optional<Transaction> save(Transaction transaction){
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(TRANSACTION_INSERT_VALUES, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, transaction.getType().name());
            preparedStatement.setObject(2, transaction.getSum());
            preparedStatement.setObject(3, transaction.getPlayerAccountFrom());
            preparedStatement.setObject(4, transaction.getPlayerAccountTo());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                transaction.setId(generatedKeys.getObject("id", UUID.class));
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, запрашивающий информацию о транзакциях с БД по номеру счета
     * @param numberAccount - номер счета
     * @return - список транзакций
     */
    public List<Transaction> findTransactionsByAccountNumber(Long numberAccount){
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
                        .playerAccountFrom(resultSet.getObject("player_account_from", Long.class))
                        .playerAccountTo(resultSet.getObject("player_account_to", Long.class))
                        .build();

                transactionList.add(transaction);
            }
            return transactionList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
