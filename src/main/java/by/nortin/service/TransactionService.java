package by.nortin.service;

import by.nortin.dto.TransactionDto;
import by.nortin.model.OperationType;
import java.math.BigDecimal;

public interface TransactionService {

//    TransactionDto saveTransaction(
//            OperationType operationType,
//            String recipientBank,
//            String sendingBank,
//            Long recipientBankAccount,
//            Long sendingBankAccount,
//            BigDecimal depositedMoney);

//    void saveTransaction(TransactionDto transactionDto);
    TransactionDto saveTransaction(TransactionDto transactionDto);
}
