package by.nortin.service.impl;

import static by.nortin.util.InjectObjectsFactory.getInstance;

import by.nortin.dto.BankAccountDto;
import by.nortin.mapper.BankAccountMapper;
import by.nortin.model.BankAccount;
import by.nortin.repository.BankAccountRepository;
import by.nortin.service.BankAccountService;
import java.math.BigDecimal;
import java.util.List;
import lombok.Setter;

@Setter
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private BankAccountMapper bankAccountMapper;

    {
        bankAccountRepository = (BankAccountRepository) getInstance(BankAccountRepository.class);
        bankAccountMapper = (BankAccountMapper) getInstance(BankAccountMapper.class);
    }

    /**
     * Implementation of the method returns a list of the user's bank account.
     *
     * @param login String
     * @return List of BankAccountDto
     */
    @Override
    public List<BankAccountDto> getUserBankAccounts(String login) {
        List<BankAccount> userBankAccounts = bankAccountRepository.getUserBankAccounts(login);
        return userBankAccounts.stream()
                .map(bankAccountMapper::convertToDto)
                .toList();
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
        return bankAccountRepository.changeBankAccountBalance(bankAccountNumber, money, replenishment);
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
        BankAccount sendingBankAccount = bankAccountMapper.convertToModel(sourceBankAccount);
        BankAccount recipientBankAccount = bankAccountMapper.convertToModel(targetBankAccount);
        bankAccountRepository.transferMoneyBetweenAccounts(sendingBankAccount, recipientBankAccount, transferredMoney);
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
        return bankAccountRepository.checkAvailabilityOfFunds(bankAccountNumber, money);
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
        return bankAccountMapper.convertToDto(bankAccountRepository.getUserBankAccountByNumberForSpecificBank(number, name));
    }

    /**
     * Implementation of the method returns all users' bank accounts of Clever-Bank.
     *
     * @return List of BankAccountDto
     */
    @Override
    public List<BankAccountDto> findAll() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(bankAccountMapper::convertToDto)
                .toList();
    }

    /**
     * Implementation of the method updates the account balance and the service date of the bank account.
     *
     * @param bankAccountDto BankAccountDto
     */
    @Override
    public void save(BankAccountDto bankAccountDto) {
        bankAccountRepository.save(bankAccountMapper.convertToModel(bankAccountDto));
    }
}
