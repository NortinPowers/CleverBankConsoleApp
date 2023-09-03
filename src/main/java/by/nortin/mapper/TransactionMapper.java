package by.nortin.mapper;

import by.nortin.dto.TransactionDto;
import by.nortin.model.Transaction;

public interface TransactionMapper {

    /**
     * The method converts the Transaction object to the TransactionDto object.
     *
     * @param transaction - object to convert
     * @return TransactionDto object
     */
    TransactionDto convertToDto(Transaction transaction);

    /**
     * The method converts the TransactionDto object to the Transaction object.
     *
     * @param transactionDto - object to convert
     * @return Transaction object
     */
    Transaction convertToModel(TransactionDto transactionDto);
}
