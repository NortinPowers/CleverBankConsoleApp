package by.nortin.repository.impl;

import by.nortin.model.Bank;
import by.nortin.model.BankAccount;
import by.nortin.repository.BankAccountRepository;
import by.nortin.repository.ConnectionPool;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BankAccountRepositoryImpl implements BankAccountRepository {

    private static final String GET_USER_BANK_ACCOUNT_BY_LOGIN = "select ba.id as bank_id, ba.number, ba.balance, c.id as currency_id, c.code "
            + "from clever_bank_accounts ba "
            + "inner join users u on u.id = ba.user_id  "
            + "inner join currencies c on c.id = ba.currency_id "
            + "where u.login = ?";
    private static final String GET_USER_BANK_ACCOUNT_BY_NUMBER = "select ba.id, ba.balance, c.code "
            + "from clever_bank_accounts ba "
            + "inner join users u on u.id = ba.user_id  "
            + "inner join currencies c on c.id = ba.currency_id "
            + "where ba.number = ?";
    private static final String UPDATE_USER_BANK_ACCOUNT_BY_ID = "update clever_bank_accounts "
            + "set balance = ?"
            + "where id = ?";

    private final ConnectionPool connectionPool;
    private final String bankName;

    private Long bankAccountId;
    private BigDecimal balance;
    private String code;
    private Long currencyId;
    private Currency currency;
    private BankAccount bankAccount;
    private Connection connection;

    {
        connectionPool = ConnectionPool.getInstance();
        connection = null;
        bankName = "Clever-Bank";
    }

    @Override
    public List<BankAccount> getUserBankAccounts(String login) {
        long number;
//        BigDecimal balance;
//        String code;
//        String bankName;
//        Currency currency;
//        BankAccount bankAccount;
        List<BankAccount> bankAccounts = new ArrayList<>();
//        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BANK_ACCOUNT_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bankAccountId = resultSet.getLong("bank_id");
                number = resultSet.getLong("number");
                balance = resultSet.getBigDecimal("balance");
                currencyId = resultSet.getLong("currency_id");
                code = resultSet.getString("code");
//                bankName = resultSet.getString("name");
                currency = Currency.getInstance(code);
                bankAccount = BankAccount.builder()
                        .id(bankAccountId)
                        .number(number)
                        .balance(balance)
                        .currencyId(currencyId)
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

    @Override
    public BigDecimal topUpBankAccount(Long bankAccountNumber, BigDecimal depositedMoney) {
        Long id = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statementRead = connection.prepareStatement(GET_USER_BANK_ACCOUNT_BY_NUMBER);
            statementRead.setLong(1, bankAccountNumber);
            ResultSet resultSetRead = statementRead.executeQuery();
            while (resultSetRead.next()) {
                id = resultSetRead.getLong("id");
                balance = resultSetRead.getBigDecimal("balance");
//                code = resultSetRead.getString("code");
//                bankName = resultSetRead.getString("name");
                //check currency?
//                currency = Currency.getInstance(code);
//                bankAccount = BankAccount.builder()
//                        .number(bankAccountNumber)
//                        .balance(balance)
//                        .currency(currency)
//                        .bank(new Bank(bankName)).build();
            }
            if (balance != null) {
                balance = balance.add(depositedMoney);
            }
            PreparedStatement statementUpdate = connection.prepareStatement(UPDATE_USER_BANK_ACCOUNT_BY_ID);
            statementUpdate.setBigDecimal(1, balance);
            statementUpdate.setLong(2, id);
            statementUpdate.execute();
            connection.commit();
        } catch (Exception e) {
            log.error("topUpBankAccount()", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("topUpBankAccount().connection.rollback()", ex);
                log.warn("info: id- " + id + " deposit: " + depositedMoney);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.error("topUpBankAccount().connection.setAutoCommit()", e);
            }
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error("Exception (topUpBankAccount(),connectionPool.closeConnection()): ", e);
                }
            }
        }
        return balance;
    }
}
