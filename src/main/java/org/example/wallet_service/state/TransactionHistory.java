package org.example.wallet_service.state;

import org.example.wallet_service.domain.entity.Transaction;
import org.example.wallet_service.domain.enums.TransactionType;
import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.factory.TransactionServiceFactory;
import org.example.wallet_service.service.TransactionService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TransactionHistory implements ConsoleState{
    private final Scanner scanner;

    private ConsoleState nextState;

    private Long accountNumber;

    private TransactionService transactionService;

    public TransactionHistory(Long accountNumber){
        scanner = ConsoleFactory.getScanner();
        transactionService = TransactionServiceFactory.getTransactionService();
        this.accountNumber = accountNumber;
    }

    @Override
    public void process() {
        System.out.println("История операций: ");
        List<Transaction> transactions = transactionService.getTransactionHistoryByNumberAccount(accountNumber);

        if (transactions != null && !transactions.isEmpty()){
            Map<TransactionType, List<String>> messages = getMessages(transactions);
            System.out.println("Переводы: ");
            System.out.println(messages.get(TransactionType.CREDIT));
            System.out.println("Пополнения: ");
            System.out.println(messages.get(TransactionType.DEBIT));
        } else {
            System.out.println("Транзакций не найдено");
        }

        nextState = new PlayerAccountState(accountNumber);
    }

    private Map<TransactionType, List<String>> getMessages(List<Transaction> transactions) {
        Map<TransactionType, List<String>> transactionsByType = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Transaction transaction : transactions){
            if (transaction.getType() == TransactionType.CREDIT){
                sb.append("To: " + transaction.getPlayerAccountTo().getPlayer().getName())
                        .append(" | type: " + transaction.getType())
                        .append(" | date: " + transaction.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                        .append(" | sum: " + transaction.getSum());

                if (!transactionsByType.containsKey(TransactionType.CREDIT)){
                    transactionsByType.put(TransactionType.CREDIT, new ArrayList<>());
                }
                transactionsByType.get(TransactionType.CREDIT).add(sb.toString());
            } else {
                sb.append("From: " + transaction.getPlayerAccountFrom().getPlayer().getName())
                        .append(" | type: " + transaction.getType())
                        .append(" | date: " + transaction.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                        .append(" | sum: " + transaction.getSum());

                if (!transactionsByType.containsKey(TransactionType.DEBIT)){
                    transactionsByType.put(TransactionType.DEBIT, new ArrayList<>());
                }
                transactionsByType.get(TransactionType.DEBIT).add(sb.toString());
            }
        }

        return transactionsByType;
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
