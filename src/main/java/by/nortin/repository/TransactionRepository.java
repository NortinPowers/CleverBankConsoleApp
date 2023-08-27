package by.nortin.repository;

import by.nortin.model.Transaction;

public interface TransactionRepository {

//    Transaction saveTransaction(
//            OperationType operationType,
//            String recipientBank,
//            String sendingBank,
//            Long recipientBankAccount,
//            Long sendingBankAccount,
//            BigDecimal depositedMoney);

    //    void saveTransaction(Transaction transaction);
    Transaction saveTransaction(Transaction transaction);
}
