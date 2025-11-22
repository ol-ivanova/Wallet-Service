package org.example.wallet_service.domain.mapper;

import org.example.wallet_service.domain.dto.TransactionDto;
import org.example.wallet_service.domain.entity.Transaction;

/**
 * Класс-маппер для Transaction
 */
public class TransactionMapper implements Mapper<TransactionDto, Transaction>{

    /**
     * Реализация singleton класса-маппера
     */
    private static final TransactionMapper TRANSACTION_MAPPER = new TransactionMapper();
    public static TransactionMapper getInstance() {
        return TRANSACTION_MAPPER;
    }

    /**
     * метод, сопоставляющий dto объект TransactionDto и сущность Transaction
     * @param transactionDto - dto объект
     * @return - соответствующая сущность
     */
    @Override
    public Transaction map(TransactionDto transactionDto) {
        return Transaction.builder()
                .type(transactionDto.getType())
                .sum(transactionDto.getSum())
                .playerAccountFrom(transactionDto.getPlayerAccountFrom())
                .playerAccountTo(transactionDto.getPlayerAccountTo())
                .build();
    }
}
