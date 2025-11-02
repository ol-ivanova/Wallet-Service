package org.example.wallet_service.state;

import org.example.wallet_service.domain.dto.CreatePlayerAccountDto;
import org.example.wallet_service.domain.dto.CreatePlayerDto;
import org.example.wallet_service.domain.dto.PlayerAuditDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.entity.PlayerAccount;
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

public class RegistrationState implements ConsoleState {

    private final Scanner scanner;

    private ConsoleState nextState;

    private PlayerAccountService playerAccountService;

    private PlayerService playerService;

    private PlayerAuditService playerAuditService;

    public RegistrationState(){
        scanner = ConsoleFactory.getScanner();
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
        playerService = PlayerServiceFactory.getPlayerService();
        playerAuditService = PlayerAuditServiceFactory.getPlayerAuditService();
    }


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

    private void createAuditLog(Player player) {
        PlayerAuditDto playerAuditDto = PlayerAuditDto.builder()
                .playerId(player.getId())
                .auditAction(AuditAction.LOGIN)
                .build();
        playerAuditService.createLog(playerAuditDto);
    }

    private void createPlayerAccount(Player player) {
        CreatePlayerAccountDto createPlayerAccountDto = CreatePlayerAccountDto.builder()
                .playerId(player.getId())
                .balance(BigDecimal.valueOf(0))
                .build();
        PlayerAccount playerAccount = playerAccountService.createAccountPlayer(createPlayerAccountDto);
        playerAccount.setPlayer(player);
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }

}
