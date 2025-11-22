package org.example.wallet_service.state;

import org.example.wallet_service.domain.dto.TransactionDto;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.domain.enums.TransactionType;
import org.example.wallet_service.exception.PlayerAccountException;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.PlayerAccountServiceFactory;
import org.example.wallet_service.factory.TransactionServiceFactory;
import org.example.wallet_service.service.PlayerAccountService;
import org.example.wallet_service.service.TransactionService;
import org.example.wallet_service.util.SelectionUtil;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Состояние - кредит операции
 */
public class CreditState implements ConsoleState {

    /**
     * Объект сканнера
     */
    private final Scanner scanner;

    /**
     * Следующее состояние приложения
     */
    private ConsoleState nextState;

    /**
     * Объект класса PlayerAccount
     */
    private PlayerAccount playerAccount;

    /**
     * Сервис для работы со счетом пользователя
     */
    private PlayerAccountService playerAccountService;

    /**
     * Сервис для работы с транзакциями пользователя
     */
    private TransactionService transactionService;

    public CreditState(PlayerAccount playerAccount) {
        scanner = ConsoleFactory.getScanner();
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
        transactionService = TransactionServiceFactory.getTransactionService();
        this.playerAccount = playerAccount;
    }

    /**
     * Метод, запускающий логику процесса credit операций
     */
    @Override
    public void process() {
        System.out.println("Укажите номер получателя");
        Long accountNumber = SelectionUtil.getValue(() -> scanner.nextLong());

        System.out.println("Укажите сумму перевода");
        BigDecimal sum = SelectionUtil.getValue(() -> scanner.nextBigDecimal());

        try {
            PlayerAccount playerAccount = playerAccountService.getAccountByNumber(accountNumber);
            playerAccount.setBalance(playerAccount.getBalance().add(sum));

            this.playerAccount.setBalance(this.playerAccount.getBalance().subtract(sum));

            playerAccountService.updateBalanceByAccountNumber(playerAccount.getBalance() , accountNumber);
            playerAccountService.updateBalanceByAccountNumber(this.playerAccount.getBalance() , this.playerAccount.getAccountNumber());

            createTransaction(sum , playerAccount);
            nextState = new PlayerAccountState(this.playerAccount.getAccountNumber());
        } catch (PlayerAccountException e) {
            System.out.println(e.getMessage());
            System.out.println("1. Попробовать снова\n 2. Вернуться назад\n Выберите необходимый вариант");

            int menuSelection = SelectionUtil.getValue(() -> Integer.parseInt(scanner.nextLine()));

            nextState = switch (menuSelection) {
                case 1 -> nextState;
                case 2 -> new PlayerAccountState(this.playerAccount.getAccountNumber());
                default -> throw new IllegalStateException("Неправильно значение" + menuSelection);
            };
        }
    }

    /**
     * Метод для фиксирования транзакции
     * @param sum - сумма перевода
     * @param playerAccount - аккаунт получателя
     */
    private void createTransaction(BigDecimal sum , PlayerAccount playerAccount) {
        TransactionDto transactionDto = TransactionDto.builder()
                .type(TransactionType.CREDIT)
                .sum(sum)
                .playerAccountFrom(this.playerAccount.getAccountNumber())
                .playerAccountTo(playerAccount.getAccountNumber())
                .build();

        transactionService.transferData(transactionDto);
        System.out.println("Перевод выполнен");
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
