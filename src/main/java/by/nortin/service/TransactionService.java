package by.nortin.service;

import by.nortin.dto.TransactionDto;

public interface TransactionService {

    /**
     * The method save the user transactionDto and returns the modified transactionDto.
     *
     * @param transactionDto TransactionDto
     * @return modified transactionDto
     */
    TransactionDto saveTransaction(TransactionDto transactionDto);
}
