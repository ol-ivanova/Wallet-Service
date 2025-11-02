package org.example.wallet_service.factory;

import org.example.wallet_service.repository.TransactionRepository;

public class TransactionRepositoryFactory {
    private static TransactionRepository transactionRepository;

    private TransactionRepositoryFactory(){}

    public static TransactionRepository getTransactionRepository(){
        if(transactionRepository == null){
            transactionRepository = new TransactionRepository();
        }
        return transactionRepository;
    }
}
