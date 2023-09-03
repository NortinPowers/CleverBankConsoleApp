package by.nortin.service;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.BankDto;
import by.nortin.dto.TransactionDto;
import java.math.BigDecimal;
import java.util.List;

public interface OperationManagerService {

    /**
     * The method returns a list of the user's bank account.
     *
     * @param login String
     * @return List of BankAccountDto
     */
    List<BankAccountDto> getUserBankAccounts(String login);

    /**
     * The method changes the bank account balance.
     *
     * @param bankAccountNumber Long
     * @param money             BigDecimal
     * @param replenishment     boolean TRUE if account replenishment
     * @return BigDecimal
     */
    BigDecimal changeBankAccountBalance(Long bankAccountNumber, BigDecimal money, boolean replenishment);

    /**
     * The method returns a list of all banks.
     *
     * @return List of BankDto
     */
    List<BankDto> getAll();

    /**
     * The method generates and saves the receipt.
     *
     * @param transactionDto TransactionDto, all info
     */
    void saveReceiptTxt(TransactionDto transactionDto);

    /**
     * The method save the user transactionDto and returns the modified transactionDto.
     *
     * @param transactionDto TransactionDto
     * @return modified transactionDto
     */
    TransactionDto saveTransaction(TransactionDto transactionDto);

    /**
     * The method transports money between 2 bank accounts.
     *
     * @param sourceBankAccount BankAccountDto
     * @param targetBankAccount BankAccountDto
     * @param transferredMoney  BigDecimal
     */
    void transferMoneyBetweenAccounts(BankAccountDto sourceBankAccount, BankAccountDto targetBankAccount, BigDecimal transferredMoney);

    /**
     * The method checks whether there are enough funds in the bank account to perform the operation.
     *
     * @param bankAccountNumber Long
     * @param money             BigDecimal
     * @return boolean
     */
    boolean checkAvailabilityOfFunds(Long bankAccountNumber, BigDecimal money);

    /**
     * The method returns a bank account by its number and bank name.
     *
     * @param number Long
     * @param name   String
     * @return BankAccount
     */
    BankAccountDto getUserBankAccountByNumberForSpecificBank(Long number, String name);
}
