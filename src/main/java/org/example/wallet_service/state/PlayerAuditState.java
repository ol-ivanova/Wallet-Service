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

public class PlayerAuditState implements ConsoleState{
    private final Scanner scanner;

    private ConsoleState nextState;

    private Integer playerId;

    private PlayerAuditService playerAuditService;

    public PlayerAuditState(Integer playerId){
        scanner = ConsoleFactory.getScanner();
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
        this.playerId = playerId;
    }


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

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
