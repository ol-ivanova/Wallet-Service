package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.TransactionDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.domain.entity.Transaction;
import org.example.wallet_service.domain.mapper.TransactionMapper;
import org.example.wallet_service.exception.TransferException;
import org.example.wallet_service.factory.TransactionRepositoryFactory;
import org.example.wallet_service.repository.TransactionRepository;

import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;


    public TransactionService(){
        transactionRepository = TransactionRepositoryFactory.getTransactionRepository();
        transactionMapper = TransactionMapper.getInstance();
    }

    /**
     * Метод для фиксирования информации о транзакции
     * @param transactionDto - dto объект
     * @return - объект Transaction
     */
    public Transaction transferData(TransactionDto transactionDto){
        Transaction transaction = transactionMapper.map(transactionDto);
        return transactionRepository.save(transaction)
                .orElseThrow(() -> new TransferException("Ошибка операции"));
    }

    /**
     * Метод для получения истории транзакций
     * @param accountNumber - номер счета
     * @return - Список транзакций
     */

    public List<Transaction> getTransactionHistoryByNumberAccount(Long accountNumber){
        List<Transaction> transactions = transactionRepository.findTransactionsByAccountNumber(accountNumber);
        return transactions;
    }
}
