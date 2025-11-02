package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.TransactionDto;
import org.example.wallet_service.domain.entity.Transaction;
import org.example.wallet_service.exception.TransferException;
import org.example.wallet_service.factory.TransactionRepositoryFactory;
import org.example.wallet_service.repository.TransactionRepository;

import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(){
        transactionRepository = TransactionRepositoryFactory.getTransactionRepository();
    }

    public Transaction transferData(TransactionDto transactionDto){
        return transactionRepository.save(transactionDto)
                .orElseThrow(() -> new TransferException("Ошибка операции"));
    }

    public List<Transaction> getTransactionHistoryByNumberAccount(Long accountNumber){
        List<Transaction> transactions = transactionRepository.findTransactionsByAccountNumber(accountNumber);
        return transactions;
    }
}
