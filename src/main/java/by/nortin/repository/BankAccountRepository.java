package by.nortin.repository;

import by.nortin.model.BankAccount;
import java.util.List;

public interface BankAccountRepository {

    List<BankAccount> getUserBankAccounts(String login);
}
