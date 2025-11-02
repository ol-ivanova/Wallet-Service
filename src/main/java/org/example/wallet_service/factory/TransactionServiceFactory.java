package org.example.wallet_service.factory;

import org.example.wallet_service.service.TransactionService;

public class TransactionServiceFactory {
    private static TransactionService transactionService;

    private TransactionServiceFactory(){}

    public static TransactionService getTransactionService(){
        if(transactionService == null){
            transactionService = new TransactionService();
        }
        return transactionService;
    }
}
