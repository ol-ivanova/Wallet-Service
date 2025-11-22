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

/**
 * Состояние - меню пользователя
 */
public class PlayerState implements ConsoleState{

    /**
     * Объект сканнера
     */
    private final Scanner scanner;

    /**
     * Следующее состояние приложения
     */
    private ConsoleState nextState;

    /**
     * id пользователя
     */
    private Integer playerId;

    /**
     * Сервис для работы со счетом пользователя
     */
    private PlayerAccountService playerAccountService;

    /**
     * Сервис для работы с логами пользователя
     */
    private PlayerAuditService playerAuditService;

    public PlayerState(Integer playerId){
        scanner = ConsoleFactory.getScanner();
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
        this.playerId = playerId;
    }

    /**
     * Метод, запускающий логику процесса работы с меню
     */
    @Override
    public void process() {
        PlayerAccount playerAccount = playerAccountService.getAccountByPlayerId(playerId);

        System.out.println("1. Операции по счету\n2. Просмотр аудита\n3. Выход");

        int menuSelection = SelectionUtil.getValue(() -> scanner.nextInt());
        switch (menuSelection) {
            case 1 -> nextState = new PlayerAccountState(playerAccount.getAccountNumber());
            case 2 -> nextState = new PlayerAuditState(playerId);
            case 3 -> {
                createAuditLog();
                nextState = new MainState();
            }
            default -> throw new IllegalStateException("Неправильное значение " + menuSelection);
        };
    }

    /**
     * Метод для фиксирования выхода пользователя
     */
    private void createAuditLog() {
        PlayerAuditDto playerAuditDto = PlayerAuditDto.builder()
                .playerId(playerId)
                .auditAction(AuditAction.LOGOUT)
                .build();
        playerAuditService.createLog(playerAuditDto);
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
