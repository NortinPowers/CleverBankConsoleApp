package by.nortin.repository;

import by.nortin.model.BankAccount;
import java.math.BigDecimal;
import java.util.List;

public interface BankAccountRepository {

    /**
     * The method returns a list of the user's bank account.
     *
     * @param login String
     * @return List of BankAccount
     */
    List<BankAccount> getUserBankAccounts(String login);

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
     * The method transports money between 2 bank accounts.
     *
     * @param sourceBankAccount BankAccount
     * @param targetBankAccount BankAccount
     * @param transferredMoney  BigDecimal
     */
    void transferMoneyBetweenAccounts(BankAccount sourceBankAccount, BankAccount targetBankAccount, BigDecimal transferredMoney);

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
    BankAccount getUserBankAccountByNumberForSpecificBank(Long number, String name);

    /**
     * The method returns all users' bank accounts of Clever-Bank.
     *
     * @return List of BankAccount
     */
    List<BankAccount> findAll();

    /**
     * The method updates the account balance and the service date of the bank account.
     *
     * @param bankAccount BankAccount
     */
    void save(BankAccount bankAccount);
}
