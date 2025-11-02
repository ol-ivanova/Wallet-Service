package org.example.wallet_service.state;

import org.example.wallet_service.domain.entity.PlayerAudit;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAuditServiceFactory;
import org.example.wallet_service.service.PlayerAuditService;
import org.example.wallet_service.util.SelectionUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PlayerAuditState implements ConsoleState{
    private final Scanner scanner;

    private ConsoleState nextState;

    private Integer playerId;

    private PlayerAuditService playerAuditService;

    public PlayerAuditState(Integer playerId){
        scanner = ConsoleFactory.getScanner();
        this.playerId = playerId;
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
    }


    @Override
    public void process() {
        System.out.println("История активности:");
        List<PlayerAudit> playerAuditList = playerAuditService.getLogsByPlayerId(playerId);
        if(playerAuditList == null||playerAuditList.isEmpty()){
            System.out.println("Активность не найдена");
        }else{
            for(PlayerAudit playerAudit : playerAuditList){
                System.out.println(playerAudit.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + " | " + playerAudit.getAction());
            }
        }

        nextState = new PlayerState(playerId);
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
