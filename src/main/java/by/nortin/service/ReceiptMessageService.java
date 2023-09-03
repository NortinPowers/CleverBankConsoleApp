package by.nortin.service;

import by.nortin.dto.TransactionDto;

public interface ReceiptMessageService {

    /**
     * The method generates a receipt based on data from the TransactionDto object.
     *
     * @param transactionDto - current transaction
     * @return String
     */
    String formReceipt(TransactionDto transactionDto);
}
