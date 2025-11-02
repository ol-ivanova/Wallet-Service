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

public class DebitState implements ConsoleState{
    private final Scanner scanner;
    /** следующее состояние приложения
     */
    private ConsoleState nextState;

    private PlayerAccount playerAccount;

    private PlayerAccountService playerAccountService;

    private TransactionService transactionService;

    public DebitState(PlayerAccount playerAccount){
        scanner = ConsoleFactory.getScanner();
        playerAccountService = PlayerAccountServiceFactory.getPlayerAccountService();
        transactionService = TransactionServiceFactory.getTransactionService();
        this.playerAccount = playerAccount;
    }

    @Override
    public void process() {
        System.out.println("Укажите номер, с которого будет пополнение");
        Long accountNumber = SelectionUtil.getValue(() -> scanner.nextLong());

        System.out.println("Укажите сумму пополнения");
        BigDecimal sum = SelectionUtil.getValue(() -> scanner.nextBigDecimal());

        try {
            PlayerAccount playerAccount = playerAccountService.getAccountByNumber(accountNumber);
            playerAccount.setBalance(playerAccount.getBalance().subtract(sum));

            this.playerAccount.setBalance(this.playerAccount.getBalance().add(sum));

            playerAccountService.updateBalanceByAccountNumber(playerAccount.getBalance(), accountNumber);
            playerAccountService.updateBalanceByAccountNumber(this.playerAccount.getBalance(), this.playerAccount.getAccountNumber());

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

    private void createTransaction(BigDecimal sum , PlayerAccount playerAccount) {
        TransactionDto transactionDto = TransactionDto.builder()
                .type(TransactionType.DEBIT)
                .sum(sum)
                .playerAccountFrom(playerAccount.getAccountNumber())
                .playerAccountTo(this.playerAccount.getAccountNumber())
                .build();

        transactionService.transferData(transactionDto);
    }

    @Override
    public ConsoleState nextState () {
        return nextState;
    }
}
