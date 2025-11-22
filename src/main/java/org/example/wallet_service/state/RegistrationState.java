package org.example.wallet_service.state;

import org.example.wallet_service.domain.dto.CreatePlayerAccountDto;
import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.dto.PlayerAuditDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.enums.AuditAction;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAccountServiceFactory;
import org.example.wallet_service.factory.PlayerAuditServiceFactory;
import org.example.wallet_service.factory.PlayerServiceFactory;
import org.example.wallet_service.service.PlayerAccountService;
import org.example.wallet_service.service.PlayerAuditService;
import org.example.wallet_service.service.PlayerService;
import org.example.wallet_service.util.SelectionUtil;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Состояние - регистрация пользователя
 */
public class RegistrationState implements ConsoleState {

    /**
     * Объект сканнера
     */
    private final Scanner scanner;

    /**
     * Следующее состояние приложения
     */
    private ConsoleState nextState;

    /**
     * Сервис для работы со счетом пользователя
     */
    private PlayerAccountService playerAccountService;

    /**
     * Сервис для работы с пользователем
     */
    private PlayerService playerService;

    /**
     * Сервис для работы с логами пользователя
     */
    private PlayerAuditService playerAuditService;

    public RegistrationState(){
        scanner = ConsoleFactory.getScanner();
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
        playerService = PlayerServiceFactory.getPlayerService();
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
    }

    /**
     * Метод, запускающий логику процесса регистрации пользователя
     */
    @Override
    public void process() {
        System.out.println("Введите имя");
        String name = SelectionUtil.getValue(()->scanner.nextLine());

        System.out.println("Введите логин");
        String username = SelectionUtil.getValue(()->scanner.nextLine());

        System.out.println("Введите пароль");
        String password = SelectionUtil.getValue(()->scanner.nextLine());

        CreatePlayerDto createPlayerDto = CreatePlayerDto.builder()
                .name(name)
                .username(username)
                .password(password)
                .build();
        try {
            Player player = playerService.createPlayer(createPlayerDto);
            System.out.println("Пользователь создан");

            createPlayerAccount(player);
            createAuditLog(player);

            System.out.println("Добро пожаловать");
            nextState = new PlayerState(player.getId());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("1. Повторить попытку\n2. Вернуться назад\nВыберете необходимый пункт");

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
     * Метод для создания счета пользователя
     * @param player - объект Player
     */
    private void createPlayerAccount(Player player) {
        CreatePlayerAccountDto createPlayerAccountDto = CreatePlayerAccountDto.builder()
                .playerId(player.getId())
                .balance(BigDecimal.valueOf(0))
                .build();
        playerAccountService.createAccountPlayer(createPlayerAccountDto);
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
