package by.nortin.service;

import by.nortin.dto.BankAccountDto;
import java.util.List;

public interface BankAccountService {

    List<BankAccountDto> getUserBankAccounts(String login);
}
