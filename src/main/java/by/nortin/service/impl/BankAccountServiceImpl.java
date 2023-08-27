package by.nortin.service.impl;

import static by.nortin.util.InjectObjectsFactory.getInstance;

import by.nortin.dto.BankAccountDto;
import by.nortin.mapper.BankAccountMapper;
import by.nortin.model.BankAccount;
import by.nortin.repository.BankAccountRepository;
import by.nortin.service.BankAccountService;
import java.math.BigDecimal;
import java.util.List;

public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    {
        bankAccountRepository = (BankAccountRepository) getInstance(BankAccountRepository.class);
        bankAccountMapper = (BankAccountMapper) getInstance(BankAccountMapper.class);
    }

    @Override
    public List<BankAccountDto> getUserBankAccounts(String login) {
        List<BankAccount> userBankAccounts = bankAccountRepository.getUserBankAccounts(login);
        return userBankAccounts.stream()
                .map(bankAccountMapper::convertToDto)
                .toList();
    }

    @Override
    public BigDecimal topUpBankAccount(Long bankAccountNumber, BigDecimal depositedMoney) {
        return bankAccountRepository.topUpBankAccount(bankAccountNumber, depositedMoney);
    }
}
