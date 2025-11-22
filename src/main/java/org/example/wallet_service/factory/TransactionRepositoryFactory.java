package org.example.wallet_service.factory;

import org.example.wallet_service.repository.TransactionRepository;

/**
 * Данный класс отвечает за создание единственного экземпляра класса TransactionRepository
 */
public class TransactionRepositoryFactory {
    private static TransactionRepository transactionRepository;

    private TransactionRepositoryFactory(){}

    public static TransactionRepository getTransactionRepository(){
        if (transactionRepository == null){
            transactionRepository = new TransactionRepository();
        }
        return transactionRepository;
    }
}
