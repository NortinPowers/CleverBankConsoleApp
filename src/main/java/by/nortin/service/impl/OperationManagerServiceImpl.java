package by.nortin.service.impl;

import static by.nortin.util.InjectObjectsFactory.getInstance;

import by.nortin.dto.BankAccountDto;
import by.nortin.dto.BankDto;
import by.nortin.dto.TransactionDto;
import by.nortin.service.BankAccountService;
import by.nortin.service.BankService;
import by.nortin.service.OperationManagerService;
import by.nortin.service.ReceiptSavingService;
import by.nortin.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;

public class OperationManagerServiceImpl implements OperationManagerService {

    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final ReceiptSavingService receiptSavingService;
    private final BankService bankService;

    {
        bankAccountService = (BankAccountService) getInstance(BankAccountService.class);
        transactionService = (TransactionService) getInstance(TransactionService.class);
        receiptSavingService = (ReceiptSavingService) getInstance(ReceiptSavingService.class);
        bankService = (BankService) getInstance(BankService.class);
    }

    /**
     * Implementation of the method returns a list of the user's bank account.
     *
     * @param login String
     * @return List of BankAccountDto
     */
    @Override
    public List<BankAccountDto> getUserBankAccounts(String login) {
        return bankAccountService.getUserBankAccounts(login);
    }

    /**
     * Implementation of the method changes the bank account balance.
     *
     * @param bankAccountNumber Long
     * @param money             BigDecimal
     * @param replenishment     boolean TRUE if account replenishment
     * @return BigDecimal
     */
    @Override
    public BigDecimal changeBankAccountBalance(Long bankAccountNumber, BigDecimal money, boolean replenishment) {
        return bankAccountService.changeBankAccountBalance(bankAccountNumber, money, replenishment);
    }

    /**
     * Implementation of the method returns a list of all banks.
     *
     * @return List of BankDto
     */
    @Override
    public List<BankDto> getAll() {
        return bankService.getAll();
    }

    /**
     * Implementation of the method generates and saves the receipt.
     *
     * @param transactionDto TransactionDto, all info
     */
    @Override
    public void saveReceiptTxt(TransactionDto transactionDto) {
        receiptSavingService.saveReceiptTxt(transactionDto);
    }

    /**
     * Implementation of save the user transactionDto and returns the modified transactionDto.
     *
     * @param transactionDto TransactionDto
     * @return modified transactionDto
     */
    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        return transactionService.saveTransaction(transactionDto);
    }

    /**
     * Implementation of the method transports money between 2 bank accounts.
     *
     * @param sourceBankAccount BankAccountDto
     * @param targetBankAccount BankAccountDto
     * @param transferredMoney  BigDecimal
     */
    @Override
    public void transferMoneyBetweenAccounts(BankAccountDto sourceBankAccount, BankAccountDto targetBankAccount, BigDecimal transferredMoney) {
        bankAccountService.transferMoneyBetweenAccounts(sourceBankAccount, targetBankAccount, transferredMoney);
    }

    /**
     * Implementation of the method checks whether there are enough funds in the bank account to perform the operation.
     *
     * @param bankAccountNumber Long
     * @param money             BigDecimal
     * @return boolean
     */
    @Override
    public boolean checkAvailabilityOfFunds(Long bankAccountNumber, BigDecimal money) {
        return bankAccountService.checkAvailabilityOfFunds(bankAccountNumber, money);
    }

    /**
     * Implementation of the method returns a bank account by its number and bank name.
     *
     * @param number Long
     * @param name   String
     * @return BankAccount
     */
    @Override
    public BankAccountDto getUserBankAccountByNumberForSpecificBank(Long number, String name) {
        return bankAccountService.getUserBankAccountByNumberForSpecificBank(number, name);
    }
}
