package by.nortin.repository.impl;

import static by.nortin.util.Constants.MethodName.CLOSE_CONNECTION;
import static by.nortin.util.Constants.MethodName.ROLLBACK;
import static by.nortin.util.Constants.MethodName.SAVE_TRANSACTION;
import static by.nortin.util.Constants.QueryVariables.ID;
import static by.nortin.util.MessageUtils.getErrorMessageToLog;
import static by.nortin.util.MessageUtils.getErrorMessageToUser;

import by.nortin.model.Bank;
import by.nortin.model.OperationType;
import by.nortin.model.Transaction;
import by.nortin.repository.ConnectionPool;
import by.nortin.repository.TransactionRepository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final String SAVE_USER_TRANSACTION = "insert into transactions (date, monies, sending_bank_id, sending_bank_account_id, recipient_bank_id, recipient_bank_account_id, currency_id, operation_type_id) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BANK_ID_BY_BANK_NAME = "select id from banks where name = ?";
    private static final String GET_OPERATION_TYPE_ID_BY_TYPE = "select id from operation_types where type = ?";
    private final ConnectionPool connectionPool;
    private Connection connection;

    {
        connectionPool = ConnectionPool.getInstance();
        connection = null;
    }

    /**
     * Implementation of the method save the user transaction and returns the modified transaction.
     *
     * @param transaction Transaction
     * @return modified transaction
     */
    @Override
    public Transaction saveTransaction(Transaction transaction) {
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            Long sendingBankAccountId = getBankAccountIdByNumber(transaction.getSendingBank(), transaction.getSendingBankAccount().getNumber(), connection)
                    .orElseThrow(() -> new SQLDataException(getIdNotFoundMessage("Sending bank account")));
            Long recipientBankAccountId = getBankAccountIdByNumber(transaction.getRecipientBank(), transaction.getRecipientBankAccount().getNumber(), connection)
                    .orElseThrow(() -> new SQLDataException(getIdNotFoundMessage("Recipient bank account")));
            Long sendingBankId = getBankIdByName(transaction.getSendingBank(), connection)
                    .orElseThrow(() -> new SQLDataException(getIdNotFoundMessage("Sending bank")));
            Long recipientBankId = getBankIdByName(transaction.getRecipientBank(), connection)
                    .orElseThrow(() -> new SQLDataException(getIdNotFoundMessage("Recipient bank")));
            Long operationTypeId = getOperationTypeIdByType(transaction.getOperationType(), connection)
                    .orElseThrow(() -> new SQLDataException(getIdNotFoundMessage("Operation type")));
            Date date = setTransactionCurrentDate(transaction);
            PreparedStatement statement = connection.prepareStatement(SAVE_USER_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, date);
            statement.setBigDecimal(2, transaction.getMonies());
            statement.setLong(3, sendingBankId);
            statement.setLong(4, sendingBankAccountId);
            statement.setLong(5, recipientBankId);
            statement.setLong(6, recipientBankAccountId);
            statement.setLong(7, transaction.getCurrencyId());
            statement.setLong(8, operationTypeId);
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                transaction.setId(id);
            }
            connection.commit();
        } catch (Exception e) {
            getErrorMessageToUser("Sorry, something wrong");
            log.error(getErrorMessageToLog(SAVE_TRANSACTION), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error(getErrorMessageToLog(SAVE_TRANSACTION, ROLLBACK), ex);
            }
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(SAVE_TRANSACTION, CLOSE_CONNECTION), e);
                }
            }
        }
        return transaction;
    }

    /**
     * The method sets the date of the transaction.
     *
     * @param transaction Transaction
     * @return Date
     */
    private static Date setTransactionCurrentDate(Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        LocalDateTime dateTime = transaction.getDate();
        long millis = dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        return new Date(millis);
    }

    /**
     * The method returns a message about the absence of an object with a certain modifier.
     *
     * @param type String
     * @return String message
     */
    private String getIdNotFoundMessage(String type) {
        return String.format("%s  not found", type);
    }

    /**
     * The method returns the id of the operation type.
     *
     * @param operationType OperationType
     * @param instance      Connection
     * @return Optional of Long
     * @throws SQLException not found
     */
    private Optional<Long> getOperationTypeIdByType(OperationType operationType, Connection instance) throws SQLException {
        Long id = null;
        PreparedStatement statement = instance.prepareStatement(GET_OPERATION_TYPE_ID_BY_TYPE);
        statement.setString(1, operationType.name().toLowerCase());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong(ID);
        }
        return Optional.ofNullable(id);
    }

    /**
     * The method returns the bank id.
     *
     * @param sendingBank Bank
     * @param instance    Connection
     * @return Optional of Long
     * @throws SQLException not found
     */
    private Optional<Long> getBankIdByName(Bank sendingBank, Connection instance) throws SQLException {
        Long id = null;
        PreparedStatement statement = instance.prepareStatement(GET_BANK_ID_BY_BANK_NAME);
        statement.setString(1, sendingBank.getName());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong(ID);
        }
        return Optional.ofNullable(id);
    }

    /**
     * The method returns the id of the bank account.
     *
     * @param sendingBank Bank
     * @param number      Long
     * @param instance    Connection
     * @return Optional of Long
     * @throws Exception not found
     */
    private Optional<Long> getBankAccountIdByNumber(Bank sendingBank, Long number, Connection instance) throws Exception {
        Long id = null;
        String query = createQuerySelectIdFromCurrentBank(sendingBank);
        PreparedStatement statement = instance.prepareStatement(query);
        statement.setLong(1, number);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong(ID);
        }
        return Optional.ofNullable(id);
    }

    /**
     * The method generates a request to select the bank id by its name.
     *
     * @param bank Bank
     * @return String query
     */
    private String createQuerySelectIdFromCurrentBank(Bank bank) {
        return "select id from " + bank.getName().toLowerCase().replace("-", "_") + "_accounts where number = ?";
    }
}
