package org.example.wallet_service.state;

import org.example.wallet_service.domain.dto.PlayerAuditDto;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.domain.enums.AuditAction;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAccountServiceFactory;
import org.example.wallet_service.factory.PlayerAuditServiceFactory;
import org.example.wallet_service.service.PlayerAccountService;
import org.example.wallet_service.service.PlayerAuditService;
import org.example.wallet_service.util.SelectionUtil;

import java.util.Scanner;

public class PlayerState implements ConsoleState{
    private final Scanner scanner;

    private ConsoleState nextState;

    private Integer playerId;

    private PlayerAccountService playerAccountService;

    private PlayerAuditService playerAuditService;

    public PlayerState(Integer playerId){
        scanner = ConsoleFactory.getScanner();
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
        this.playerId = playerId;
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
    }
    @Override
    public void process() {
        PlayerAccount playerAccount = playerAccountService.getAccountByPlayerID(playerId);

        System.out.println("1. Операции по счету\n2. Просмотр аудита\n3. Выход");

        int menuSelection = SelectionUtil.getValue(() -> scanner.nextInt());
        switch (menuSelection) {
            case 1 -> nextState = new PlayerAccountState(playerAccount.getAccountNumber());
            case 2 -> nextState = new PlayerAuditState(playerId);
            case 3 -> {
                PlayerAuditDto playerAuditDto = PlayerAuditDto.builder()
                        .playerId(playerId)
                        .auditAction(AuditAction.LOGOUT)
                        .build();
                playerAuditService.createLog(playerAuditDto);
                nextState = new MainState();
            }
            default -> throw new IllegalStateException("Неправильное значение " + menuSelection);

        };
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
