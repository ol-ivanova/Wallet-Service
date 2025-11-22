package org.example.wallet_service.repository;

import org.example.wallet_service.domain.entity.PlayerAudit;
import org.example.wallet_service.domain.enums.AuditAction;
import org.example.wallet_service.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlayerAuditRepository {
    public static final String CREATE_LOG = """
            INSERT INTO wallet_schema.player_audit (action, player_id) VALUES (?,?);
            """;

    public static final String FIND_LOG_BY_PLAYER_ID = """
            SELECT * FROM wallet_schema.player_audit WHERE player_id = ?;
            """;

    /**
     * Метод для сохранения логов в БД
     * @param playerAudit - объект playerAudit
     */
    public void save(PlayerAudit playerAudit) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_LOG)) {
            preparedStatement.setObject(1, playerAudit.getAction().name());
            preparedStatement.setObject(2, playerAudit.getPlayerId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для получения логов из Бд по id пользователя
     * @param playerId - id пользователя
     * @return - список логов
     */
    public List<PlayerAudit> findLogsByPlayerId(int playerId) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_LOG_BY_PLAYER_ID)) {
            preparedStatement.setObject(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PlayerAudit> playerAuditList = new ArrayList<>();
            while (resultSet.next()){
                PlayerAudit playerAudit = PlayerAudit.builder()
                        .id(resultSet.getObject("id", Integer.class))
                        .dateTime(resultSet.getObject("date_time", LocalDateTime.class))
                        .action(AuditAction.valueOf(resultSet.getString("action")))
                        .playerId(resultSet.getObject("player_id", Integer.class))
                        .build();

                playerAuditList.add(playerAudit);
            }
            return playerAuditList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
