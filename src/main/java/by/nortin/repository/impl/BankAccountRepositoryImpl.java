package by.nortin.repository.impl;

import by.nortin.model.Bank;
import by.nortin.model.BankAccount;
import by.nortin.repository.BankAccountRepository;
import by.nortin.repository.ConnectionPool;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BankAccountRepositoryImpl implements BankAccountRepository {

    private static final String GET_USER_BANK_ACCOUNT_BY_LOGIN = "select ba.number, ba.balance, c.code, b.name "
            + "from bank_accounts ba "
            + "inner join users u on u.id = ba.user_id  "
            + "inner join currencies c on c.id = ba.currency_id "
            + "inner join banks b on b.id = ba.bank_id  "
            + "where u.login = ?";

    private final ConnectionPool connectionPool;

    {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public List<BankAccount> getUserBankAccounts(String login) {
        long number;
        BigDecimal balance;
        String code;
        String bankName;
        Currency currency;
        Connection connection = null;
        BankAccount bankAccount;
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BANK_ACCOUNT_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getLong("number");
                balance = resultSet.getBigDecimal("balance");
                code = resultSet.getString("code");
                bankName = resultSet.getString("name");
                currency = Currency.getInstance(code);
                bankAccount = BankAccount.builder()
                        .number(number)
                        .balance(balance)
                        .currency(currency)
                        .bank(new Bank(bankName)).build();
                bankAccounts.add(bankAccount);
            }
        } catch (Exception e) {
            log.error("getUserBankAccounts()", e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error("Exception (getUserBankAccounts(),connectionPool.closeConnection()): ", e);
                }
            }
        }
        return bankAccounts;
    }
}
