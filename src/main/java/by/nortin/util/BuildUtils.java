package by.nortin.util;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.TransactionDto;
import by.nortin.model.OperationType;
import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BuildUtils {

    /**
     * The method is intended for constructing a simplified TransactionsDto object.
     *
     * @param sourceBankAccount - BankAccount object
     * @param operationType     - OperationType instance
     * @param amountOfFunds     - BigDecimal object
     * @return TransactionDto object including empty fields
     */
    public static TransactionDto buildSimpleTransactionDto(BankAccountDto sourceBankAccount, OperationType operationType, BigDecimal amountOfFunds) {
        return TransactionDto.builder()
                .currencyId(sourceBankAccount.getCurrencyId())
                .currency(sourceBankAccount.getCurrency())
                .monies(amountOfFunds)
                .operationType(operationType)
                .sendingBankDto(sourceBankAccount.getBankDto())
                .sendingBankAccountDto(sourceBankAccount)
                .build();
    }

    /**
     * The method is intended for constructing a TransactionsDto object.
     *
     * @param sourceBankAccount - BankAccount object
     * @param targetBankAccount - BankAccount object
     * @param operationType     - OperationType instance
     * @param amountOfFunds     - BigDecimal object
     * @return TransactionDto object
     */
    public static TransactionDto buildTransactionDto(BankAccountDto sourceBankAccount, BankAccountDto targetBankAccount, OperationType operationType, BigDecimal amountOfFunds) {
        TransactionDto transactionDto = buildSimpleTransactionDto(sourceBankAccount, operationType, amountOfFunds);
        transactionDto.setRecipientBankDto(targetBankAccount.getBankDto());
        transactionDto.setRecipientBankAccountDto(targetBankAccount);
        return transactionDto;
    }
}
