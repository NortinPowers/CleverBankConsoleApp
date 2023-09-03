package by.nortin.service;

import by.nortin.dto.TransactionDto;

public interface ReceiptSavingService {

    /**
     * The method generates and saves the receipt.
     *
     * @param transactionDto TransactionDto, all info
     */
    void saveReceiptTxt(TransactionDto transactionDto);
}
