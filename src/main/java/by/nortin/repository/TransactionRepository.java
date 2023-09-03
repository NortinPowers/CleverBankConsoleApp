package by.nortin.repository;

import by.nortin.model.Transaction;

public interface TransactionRepository {

    /**
     * The method save the user transaction and returns the modified transaction.
     *
     * @param transaction Transaction
     * @return modified transaction
     */
    Transaction saveTransaction(Transaction transaction);
}
