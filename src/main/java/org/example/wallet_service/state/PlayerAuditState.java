package org.example.wallet_service.state;

import org.example.wallet_service.domain.entity.PlayerAudit;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAuditServiceFactory;
import org.example.wallet_service.service.PlayerAuditService;
import org.example.wallet_service.util.SelectionUtil;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Состояние - логи пользователя
 */
public class PlayerAuditState implements ConsoleState{

    /**
     * Следующее состояние приложения
     */
    private ConsoleState nextState;

    /**
     * id пользователя
     */
    private Integer playerId;

    /**
     * Сервис для работы с логами пользователя
     */
    private PlayerAuditService playerAuditService;

    public PlayerAuditState(Integer playerId){
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
        this.playerId = playerId;
    }

    /**
     * Метод, запускающий логику процесса отображения логов пользователя
     */
    @Override
    public void process() {
        System.out.println("История активности:");
        List<PlayerAudit> playerAuditList = playerAuditService.getLogsByPlayerId(playerId);
        if (playerAuditList != null && !playerAuditList.isEmpty()){
            for (PlayerAudit playerAudit : playerAuditList){
                System.out.println(playerAudit.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                        + " | " + playerAudit.getAction());
            }
        } else {
            System.out.println("Активность не найдена");
        }

        nextState = new PlayerState(playerId);
    }

    /**
     * Метод, возвращающий следующее состояние приложения
     * @return - следующее состояние приложения
     */
    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
