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

/**
 * Состояние - истории транзакций
 */
public class TransactionHistory implements ConsoleState{

    /**
     * Следующее состояние приложения
     */
    private ConsoleState nextState;

    /**
     * Номер счета
     */
    private Long accountNumber;

    /**
     * Сервис для работы с транзакциями пользователя
     */
    private TransactionService transactionService;

    public TransactionHistory(Long accountNumber){
        transactionService = TransactionServiceFactory.getTransactionService();
        this.accountNumber = accountNumber;
    }

    /**
     * Метод, запускающий логику процесса истории транзакций
     */
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

    /**
     * Метод, запускающий логику процесса отображения транзакций
     */
    private Map<TransactionType, List<String>> getMessages(List<Transaction> transactions) {
        Map<TransactionType, List<String>> transactionsByType = new HashMap<>();
        for (Transaction transaction : transactions){
            if (transaction.getType() == TransactionType.CREDIT){
                StringBuilder sb = new StringBuilder();
                sb.append("To: " + transaction.getPlayerAccountTo())
                        .append(" | type: " + transaction.getType())
                        .append(" | date: " + transaction.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                        .append(" | sum: " + transaction.getSum());

                if (!transactionsByType.containsKey(TransactionType.CREDIT)){
                    transactionsByType.put(TransactionType.CREDIT, new ArrayList<>());
                }
                transactionsByType.get(TransactionType.CREDIT).add(sb.toString());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("From: " + transaction.getPlayerAccountFrom())
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

    /**
     * Метод, возвращающий следующее состояние приложения
     * @return - следующее состояние приложения
     */
    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
