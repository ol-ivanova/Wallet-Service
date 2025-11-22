package org.example.wallet_service.state;

import org.example.wallet_service.domain.dto.AuthorizationDto;
import org.example.wallet_service.domain.dto.PlayerAuditDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.enums.AuditAction;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAuditServiceFactory;
import org.example.wallet_service.factory.PlayerServiceFactory;
import org.example.wallet_service.service.PlayerAuditService;
import org.example.wallet_service.service.PlayerService;
import org.example.wallet_service.util.SelectionUtil;

import java.util.Scanner;

/**
 * Состояние - авторизация пользователя
 */
public class AuthorizationState implements ConsoleState {

    /**
     * Объект сканнера
     */
    private final Scanner scanner;

    /**
     * Следующее состояние
     */
    private ConsoleState nextState;

    /**
     * Сервис для работы с пользователем
     */
    private PlayerService playerService;

    /**
     * Сервис для работы с логами пользователя
     */
    private PlayerAuditService playerAuditService;

    public AuthorizationState(){
        scanner = ConsoleFactory.getScanner();
        playerService = PlayerServiceFactory.getPlayerService();
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
    }

    /**
     * Метод, запускающий логику процесса авторизации пользователя
     */
    @Override
    public void process() {
        System.out.println("Введите логин");
        String username = SelectionUtil.getValue(() -> scanner.nextLine());

        System.out.println("Введите пароль");
        String password = SelectionUtil.getValue(() -> scanner.nextLine());

        try{
            AuthorizationDto authorizationDto = AuthorizationDto.builder()
                    .username(username)
                    .password(password)
                    .build();
            Player player = playerService.findByCredentials(authorizationDto.getUsername(), authorizationDto.getPassword());

            createAuditLog(player);

            System.out.println("Добро пожаловать");
            nextState = new PlayerState(player.getId());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.out.println("1. Повторить попытку\n2. Вернуться назад");
            System.out.println("Выберете необходимый пункт");
            int menuSelection = SelectionUtil.getValue(() -> Integer.parseInt(scanner.nextLine()));
            nextState = switch (menuSelection){
                case 1 -> nextState;
                case 2 -> new MainState();
                default -> throw new IllegalStateException("Неправильное значение " + menuSelection);
            };
        }
    }

    /**
     * Метод для фиксирования входа пользователя
     * @param player - объект Player
     */
    private void createAuditLog(Player player) {
        PlayerAuditDto playerAuditDto = PlayerAuditDto.builder()
                .playerId(player.getId())
                .auditAction(AuditAction.LOGIN)
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
