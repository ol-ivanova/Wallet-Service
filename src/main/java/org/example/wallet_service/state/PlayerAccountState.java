package org.example.wallet_service.state;

import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAccountServiceFactory;
import org.example.wallet_service.service.PlayerAccountService;
import org.example.wallet_service.util.SelectionUtil;

import java.util.Scanner;

public class PlayerAccountState implements ConsoleState{
    private final Scanner scanner;

    private ConsoleState nextState;

    private Long accountNumber;

    private PlayerAccountService playerAccountService;


    public PlayerAccountState(Long accountNumber){
        scanner = ConsoleFactory.getScanner();
        this.accountNumber = accountNumber;
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
    }

    @Override
    public void process() {
        PlayerAccount playerAccount = playerAccountService.getAccountByNumber(accountNumber);
        System.out.println("Баланс:" + playerAccount.getBalance());
        System.out.println("1. История операций\n2. Перевод\n3. Пополнение\n4. Вернуться назад");
        System.out.println("Введите нужный вариант:" );

        int menuSelection = SelectionUtil.getValue(() -> scanner.nextInt());

        nextState = switch (menuSelection){
            case 1 -> new TransactionHistory(playerAccount.getAccountNumber());
            case 2 -> new CreditState(playerAccount);
            case 3 -> new DebitState(playerAccount);
            case 4 -> new PlayerState(playerAccount.getPlayer().getId());
            default -> throw new IllegalStateException("Неправильное значение: " + menuSelection);
        };
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
