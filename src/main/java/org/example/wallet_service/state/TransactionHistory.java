package org.example.wallet_service.state;

import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.domain.entity.Transaction;
import org.example.wallet_service.domain.enums.TransactionType;
import org.example.wallet_service.exceptions.TransactionException;
import org.example.wallet_service.exceptions.TransferException;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.TransactionServiceFactory;
import org.example.wallet_service.service.TransactionService;
import org.example.wallet_service.util.SelectionUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TransactionHistory implements ConsoleState{
    private final Scanner scanner;

    private ConsoleState nextState;

    private Long accountNumber;

    private TransactionService transactionService;

    public TransactionHistory(Long accountNumber){
        scanner = ConsoleFactory.getScanner();
        this.accountNumber = accountNumber;
        transactionService = TransactionServiceFactory.getTransactionService();
    }

    @Override
    public void process() {
            System.out.println("История операций: ");
        List<Transaction> transactions = transactionService.getTransactionHistoryByNumberAccount(accountNumber);
        if(transactions == null || transactions.isEmpty()){
            System.out.println("Транзакций не найдено");
        }else{
            System.out.println("Переводы: ");
            for(Transaction transaction : transactions){
                if(transaction.getType() == TransactionType.CREDIT){
                    System.out.println("To: " + transaction.getPlayerAccountTo().getPlayer().getName()
                            + " | " + "type: " + transaction.getType()
                            + " | " + "date: " + transaction.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                            + " | " + "sum: " + transaction.getSum());
                }
            }
            System.out.println("Пополнения: ");
            for(Transaction transaction : transactions){
                if(transaction.getType() == TransactionType.DEBIT){
                    System.out.println("From: " + transaction.getPlayerAccountFrom().getPlayer().getName()
                            + " | " + "type: " + transaction.getType()
                            + " | " + "date: " + transaction.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                            + " | " + "sum: " + transaction.getSum());
                }
            }
        }
            nextState = new PlayerAccountState(accountNumber);
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
