package by.nortin.repository.impl;

import static by.nortin.util.Constants.CLEVER_BANK;
import static by.nortin.util.Constants.MethodName.CHECK_AVAILABILITY_OF_FUNDS;
import static by.nortin.util.Constants.MethodName.CLOSE_CONNECTION;
import static by.nortin.util.Constants.MethodName.FIND_ALL;
import static by.nortin.util.Constants.MethodName.GET_USER_BANK_ACCOUNTS;
import static by.nortin.util.Constants.MethodName.GET_USER_BANK_ACCOUNT_BY_NUMBER_FOR_SPECIFIC_BANK;
import static by.nortin.util.Constants.MethodName.ROLLBACK;
import static by.nortin.util.Constants.MethodName.SAVE;
import static by.nortin.util.Constants.MethodName.SET_AUTO_COMMIT;
import static by.nortin.util.Constants.MethodName.TOP_UP_BANK_ACCOUNT;
import static by.nortin.util.Constants.MethodName.TRANSFER_MONEY_BETWEEN_ACCOUNTS;
import static by.nortin.util.Constants.QueryVariables.ACCOUNT_OPEN_DATE;
import static by.nortin.util.Constants.QueryVariables.BALANCE;
import static by.nortin.util.Constants.QueryVariables.CODE;
import static by.nortin.util.Constants.QueryVariables.CURRENCY_ID;
import static by.nortin.util.Constants.QueryVariables.DATE_OF_LAST_SERVICE;
import static by.nortin.util.Constants.QueryVariables.ID;
import static by.nortin.util.Constants.QueryVariables.NUMBER;
import static by.nortin.util.MessageUtils.getErrorMessageToLog;

import by.nortin.model.Bank;
import by.nortin.model.BankAccount;
import by.nortin.repository.BankAccountRepository;
import by.nortin.repository.ConnectionPool;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BankAccountRepositoryImpl implements BankAccountRepository {

    private static final String GET_USER_BANK_ACCOUNT_BY_LOGIN = "select ba.id as id, ba.number, ba.balance, ba.account_open_date, ba.date_of_last_service, c.id as currency_id, c.code "
            + "from clever_bank_accounts ba "
            + "inner join users u on u.id = ba.user_id  "
            + "inner join currencies c on c.id = ba.currency_id "
            + "where u.login = ?";
    private static final String GET_USER_BANK_ACCOUNT_BY_NUMBER = "select ba.id, ba.balance, c.id as currency_id, c.code "
            + "from clever_bank_accounts ba "
            + "inner join users u on u.id = ba.user_id  "
            + "inner join currencies c on c.id = ba.currency_id "
            + "where ba.number = ?";
    private static final String UPDATE_BALANCE_BY_ID = "update clever_bank_accounts "
            + "set balance = ? "
            + "where id = ?";
    private static final String UPDATE_BALANCE_AND_SERVICE_DATE_BY_ID = "update clever_bank_accounts "
            + "set balance = ?, date_of_last_service = now() "
            + "where id = ?";
    private static final String GET_ALL_BANK_ACCOUNTS = "select * from clever_bank_accounts";

    private final ConnectionPool connectionPool;

    private Long id;
    private Long number;
    private BigDecimal balance;
    private Long bankAccountId;
    private String bankName;
    private String code;
    private Long currencyId;
    private Currency currency;
    private BankAccount bankAccount;
    private Connection connection;

    {
        connectionPool = ConnectionPool.getInstance();
        connection = null;
        bankName = CLEVER_BANK;
        bankAccount = new BankAccount();
    }

    /**
     * Implementation of the method returns a list of the user's bank account.
     *
     * @param login String
     * @return List of BankAccount
     */
    @Override
    public List<BankAccount> getUserBankAccounts(String login) {
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BANK_ACCOUNT_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bankAccountId = resultSet.getLong(ID);
                number = resultSet.getLong(NUMBER);
                balance = resultSet.getBigDecimal(BALANCE);
                currencyId = resultSet.getLong(CURRENCY_ID);
                code = resultSet.getString(CODE);
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
            log.error(getErrorMessageToLog(GET_USER_BANK_ACCOUNTS), e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(GET_USER_BANK_ACCOUNTS, CLOSE_CONNECTION), e);
                }
            }
        }
        return bankAccounts;
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
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statementRead = connection.prepareStatement(GET_USER_BANK_ACCOUNT_BY_NUMBER);
            statementRead.setLong(1, bankAccountNumber);
            ResultSet resultSetRead = statementRead.executeQuery();
            while (resultSetRead.next()) {
                id = resultSetRead.getLong(ID);
                balance = resultSetRead.getBigDecimal(BALANCE);
            }
            if (balance != null) {
                if (replenishment) {
                    balance = balance.add(money);
                } else {
                    balance = balance.subtract(money);
                }
            }
            PreparedStatement statementUpdate = connection.prepareStatement(UPDATE_BALANCE_BY_ID);
            statementUpdate.setBigDecimal(1, balance);
            statementUpdate.setLong(2, id);
            statementUpdate.execute();
            connection.commit();
        } catch (Exception e) {
            log.error(getErrorMessageToLog(TOP_UP_BANK_ACCOUNT), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error(getErrorMessageToLog(TOP_UP_BANK_ACCOUNT, ROLLBACK), ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.error(getErrorMessageToLog(TOP_UP_BANK_ACCOUNT, SET_AUTO_COMMIT), e);
            }
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(TOP_UP_BANK_ACCOUNT, CLOSE_CONNECTION), e);
                }
            }
        }
        return balance;
    }

    /**
     * Implementation of the method transports money between 2 bank accounts.
     *
     * @param sourceBankAccount BankAccount
     * @param targetBankAccount BankAccount
     * @param transferredMoney  BigDecimal
     */
    @Override
    public void transferMoneyBetweenAccounts(BankAccount sourceBankAccount, BankAccount targetBankAccount, BigDecimal transferredMoney) {
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            BigDecimal sourceBalanceNew = sourceBankAccount.getBalance().subtract(transferredMoney);
            PreparedStatement statementUpdateSource = connection.prepareStatement(createQueryUpdateUserBankAccountByNumberFromSpecificBank(sourceBankAccount.getBank()));
            statementUpdateSource.setBigDecimal(1, sourceBalanceNew);
            statementUpdateSource.setLong(2, sourceBankAccount.getNumber());
            statementUpdateSource.execute();
            BigDecimal targetBalanceNew = targetBankAccount.getBalance().add(transferredMoney);
            PreparedStatement statementUpdateTarget = connection.prepareStatement(createQueryUpdateUserBankAccountByNumberFromSpecificBank(targetBankAccount.getBank()));
            statementUpdateTarget.setBigDecimal(1, targetBalanceNew);
            statementUpdateTarget.setLong(2, targetBankAccount.getNumber());
            statementUpdateTarget.execute();
            connection.commit();
        } catch (Exception e) {
            log.error(getErrorMessageToLog(TRANSFER_MONEY_BETWEEN_ACCOUNTS), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error(getErrorMessageToLog(TRANSFER_MONEY_BETWEEN_ACCOUNTS, ROLLBACK), ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.error(getErrorMessageToLog(TRANSFER_MONEY_BETWEEN_ACCOUNTS, SET_AUTO_COMMIT), e);
            }
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(TRANSFER_MONEY_BETWEEN_ACCOUNTS, CLOSE_CONNECTION), e);
                }
            }
        }
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
        boolean flag = false;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statementRead = connection.prepareStatement(GET_USER_BANK_ACCOUNT_BY_NUMBER);
            statementRead.setLong(1, bankAccountNumber);
            ResultSet resultSet = statementRead.executeQuery();
            while (resultSet.next()) {
                balance = resultSet.getBigDecimal(BALANCE);
                if (balance.compareTo(money) > 0) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            log.error(getErrorMessageToLog(CHECK_AVAILABILITY_OF_FUNDS), e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(CHECK_AVAILABILITY_OF_FUNDS, CLOSE_CONNECTION), e);
                }
            }
        }
        return flag;
    }

    /**
     * Implementation of the method returns a bank account by its number and bank name.
     *
     * @param accountNumber Long
     * @param name          String
     * @return BankAccount
     */
    @Override
    public BankAccount getUserBankAccountByNumberForSpecificBank(Long accountNumber, String name) {
        BankAccount account = new BankAccount();
        try {
            connection = connectionPool.getConnection();
            String query = createQueryGetUserBankAccountByNumberFromSpecificBank(accountNumber, name);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong(ID);
                balance = resultSet.getBigDecimal(BALANCE);
                code = resultSet.getString(CODE);
                currencyId = resultSet.getLong(CURRENCY_ID);
                currency = Currency.getInstance(code);
                account = BankAccount.builder()
                        .number(accountNumber)
                        .balance(balance)
                        .currencyId(currencyId)
                        .currency(currency)
                        .bank(new Bank(name))
                        .build();
            }
        } catch (Exception e) {
            log.error(getErrorMessageToLog(GET_USER_BANK_ACCOUNT_BY_NUMBER_FOR_SPECIFIC_BANK), e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(GET_USER_BANK_ACCOUNT_BY_NUMBER_FOR_SPECIFIC_BANK, CLOSE_CONNECTION), e);
                }
            }
        }
        return account;
    }

    /**
     * Implementation of the method returns all users' bank accounts of Clever-Bank.
     *
     * @return List of BankAccount
     */
    @Override
    public List<BankAccount> findAll() {
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BANK_ACCOUNTS);
            ResultSet resultSet = statement.executeQuery();
            LocalDate accountOpeningDate = null;
            Date accountOpeningDateTemp;
            LocalDate dateOfLastService = null;
            Date dateOfLastServiceTemp;
            while (resultSet.next()) {
                bankAccountId = resultSet.getLong(ID);
                number = resultSet.getLong(NUMBER);
                balance = resultSet.getBigDecimal(BALANCE);
                currencyId = resultSet.getLong(CURRENCY_ID);
                accountOpeningDateTemp = resultSet.getDate(ACCOUNT_OPEN_DATE);
                if (accountOpeningDateTemp != null) {
                    accountOpeningDate = accountOpeningDateTemp.toLocalDate();
                }
                dateOfLastServiceTemp = resultSet.getDate(DATE_OF_LAST_SERVICE);
                if (dateOfLastServiceTemp != null) {
                    dateOfLastService = dateOfLastServiceTemp.toLocalDate();
                }
                bankAccount = BankAccount.builder()
                        .id(bankAccountId)
                        .number(number)
                        .balance(balance)
                        .currencyId(currencyId)
                        .accountOpeningDate(accountOpeningDate)
                        .dateOfLastService(dateOfLastService)
                        .bank(new Bank(bankName))
                        .build();
                bankAccounts.add(bankAccount);
            }
        } catch (Exception e) {
            log.error(getErrorMessageToLog(FIND_ALL), e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(FIND_ALL, CLOSE_CONNECTION), e);
                }
            }
        }
        return bankAccounts;
    }

    /**
     * Implementation of the method updates the account balance and the service date of the bank account.
     *
     * @param updatedBankAccount BankAccount
     */
    @Override
    public void save(BankAccount updatedBankAccount) {
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statementUpdate = connection.prepareStatement(UPDATE_BALANCE_AND_SERVICE_DATE_BY_ID);
            statementUpdate.setBigDecimal(1, updatedBankAccount.getBalance());
            statementUpdate.setLong(2, updatedBankAccount.getId());
            statementUpdate.execute();
        } catch (Exception e) {
            log.error(getErrorMessageToLog(SAVE), e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(SAVE, CLOSE_CONNECTION), e);
                }
            }
        }
    }

    /**
     * The method generates a request by bank account number depending on the name of the bank.
     *
     * @param accountNumber Long
     * @param name          String
     * @return String query
     */
    private String createQueryGetUserBankAccountByNumberFromSpecificBank(Long accountNumber, String name) {
        return "select ba.id, ba.balance, c.id as currency_id, c.code "
                + "from " + getCorrectBankNameForRequest(name) + "_accounts ba "
                + "inner join users u on u.id = ba.user_id  "
                + "inner join currencies c on c.id = ba.currency_id "
                + "where ba.number = " + accountNumber;
    }

    /**
     * The method returns the correct bank name for the request.
     *
     * @param bank Bank
     * @return String bankName
     */
    private String getCorrectBankNameForRequest(Bank bank) {
        return getFormattedName(bank.getName());
    }

    /**
     * The method returns the correct name for the request.
     *
     * @param name String
     * @return String bankName
     */
    private String getCorrectBankNameForRequest(String name) {
        return getFormattedName(name);
    }

    /**
     * The method format bank name.
     *
     * @param name String
     * @return String bankName
     */
    private String getFormattedName(String name) {
        return name.toLowerCase().replace("-", "_");
    }

    /**
     * The method generates a request to update the bank account by number for a specific bank.
     *
     * @param bank Bank
     * @return String query
     */
    private String createQueryUpdateUserBankAccountByNumberFromSpecificBank(Bank bank) {
        return "update " + getCorrectBankNameForRequest(bank) + "_accounts"
                + " set balance = ?"
                + " where number = ?";
    }
}
