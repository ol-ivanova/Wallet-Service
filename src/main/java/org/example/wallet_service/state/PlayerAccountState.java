package org.example.wallet_service.state;

import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAccountServiceFactory;
import org.example.wallet_service.service.PlayerAccountService;
import org.example.wallet_service.util.SelectionUtil;

import java.util.Scanner;

/**
 * Состояние - счет пользователя
 */
public class PlayerAccountState implements ConsoleState{

    /**
     * Объект сканнера
     */
    private final Scanner scanner;

    /**
     * Следующее состояние приложения
     */
    private ConsoleState nextState;

    /**
     * Номер счета
     */
    private Long accountNumber;

    /**
     * Сервис для работы со счетом пользователя
     */
    private PlayerAccountService playerAccountService;


    public PlayerAccountState(Long accountNumber){
        scanner = ConsoleFactory.getScanner();
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
        this.accountNumber = accountNumber;
    }

    /**
     * Метод, запускающий логику процесса работы со счетом
     */
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
            case 4 -> new PlayerState(playerAccount.getPlayerId());
            default -> throw new IllegalStateException("Некорректное значение: " + menuSelection);
        };
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
