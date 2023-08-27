package by.nortin.repository;

import by.nortin.model.BankAccount;
import java.math.BigDecimal;
import java.util.List;

public interface BankAccountRepository {

    List<BankAccount> getUserBankAccounts(String login);

    BigDecimal topUpBankAccount(Long bankAccountNumber, BigDecimal depositedMoney);
}
