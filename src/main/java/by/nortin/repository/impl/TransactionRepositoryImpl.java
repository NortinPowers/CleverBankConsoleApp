package by.nortin.repository.impl;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final String SAVE_USER_TRANSACTION = "insert into transactions (date, monies, sending_bank_id, sending_bank_account_id, recipient_bank_id, recipient_bank_account_id, currency_id, operation_type_id) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";
//            + "values (NOW(), ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BANK_ID_BY_BANK_NAME = "select id from banks where name = ?";
    private static final String GET_OPERATION_TYPE_ID_BY_TYPE = "select id from operation_types where type = ?";
    private final ConnectionPool connectionPool;
    private Connection connection;

    {
        connectionPool = ConnectionPool.getInstance();
        connection = null;
    }

    @Override
//    public Transaction saveTransaction(
//            OperationType operationType,
//            String recipientBank,
//            String sendingBank,
//            Long recipientBankAccount,
//            Long sendingBankAccount,
//            BigDecimal depositedMoney) {
    public Transaction saveTransaction(Transaction transaction) {
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            Long sendingBankAccountId = getBankAccountIdByNumber(transaction.getSendingBank(), transaction.getSendingBankAccount().getNumber(), connection);
            Long recipientBankAccountId;
            if (sendingBankAccountId == null) {
//                throw new SQLDataException("Bank account id not found");
                throw new SQLDataException(getIdNotFoundMessage("Sending bank account"));
            }
            if (transaction.getRecipientBankAccount() == null) {
                recipientBankAccountId = sendingBankAccountId;
                transaction.setRecipientBankAccount(transaction.getSendingBankAccount());
            } else {
                recipientBankAccountId = getBankAccountIdByNumber(transaction.getRecipientBank(), transaction.getRecipientBankAccount().getNumber(), connection);
            }
            if (recipientBankAccountId == null) {
//                throw new SQLDataException("Bank account id not found");
                throw new SQLDataException(getIdNotFoundMessage("Recipient bank"));
            }
            Long sendingBankId = getBankIdByName(transaction.getSendingBank(), connection);
            if (sendingBankId == null) {
//                throw new SQLDataException("Sending bank id not found");
                throw new SQLDataException(getIdNotFoundMessage("Sending bank account"));
            }
            Long recipientBankId;
            if (transaction.getRecipientBank() == null) {
                recipientBankId = sendingBankId;
                transaction.setRecipientBank(transaction.getSendingBank());
            } else {
                recipientBankId = getBankIdByName(transaction.getRecipientBank(), connection);
            }
            if (recipientBankId == null) {
//                throw new SQLDataException("Recipient bank id not found");
                throw new SQLDataException(getIdNotFoundMessage("Recipient bank account"));
            }
            Long operationTypeId = getOperationTypeIdByType(transaction.getOperationType(), connection);
            if (operationTypeId == null) {
//                throw new SQLDataException("Operation type id not found");
                throw new SQLDataException(getIdNotFoundMessage("Operation type"));
            }
            transaction.setDate(LocalDateTime.now());
            LocalDateTime dateTime = transaction.getDate();
            long millis = dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
            Date date = new Date(millis);
//            Date date = new Date(Date.from(transaction.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
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
                System.out.println("getGeneratedKeys() test, id: " + id);
            }
            connection.commit();
        } catch (Exception e) {
            getErrorMessageToUser("Sorry, something wrong");
            log.error("Exception saveTransaction()", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Exception saveTransaction().connection.rollback()", ex);
            }
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error("Exception saveTransaction().connectionPool.closeConnection(): ", e);
                }
            }
        }
        return transaction;
    }

    private String getIdNotFoundMessage(String type) {
        return String.format("%s  not found", type);
    }

    private Long getOperationTypeIdByType(OperationType operationType, Connection instance) throws SQLException {
        Long id = null;
        PreparedStatement statement = instance.prepareStatement(GET_OPERATION_TYPE_ID_BY_TYPE);
        statement.setString(1, operationType.name().toLowerCase());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong("id");
        }
        return id;
    }

    private Long getBankIdByName(Bank sendingBank, Connection instance) throws SQLException {
        Long id = null;
        PreparedStatement statement = instance.prepareStatement(GET_BANK_ID_BY_BANK_NAME);
        statement.setString(1, sendingBank.getName());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong("id");
        }
        return id;
    }

    private Long getBankAccountIdByNumber(Bank sendingBank, Long number, Connection instance) throws Exception {
        Long id = null;
//        try {
//            String query = "select id from "+ sendingBank.getName().toLowerCase() + "_accounts where number = ?";
        String query = createQuery(sendingBank);
//        connection = connectionPool.getConnection();
        PreparedStatement statement = instance.prepareStatement(query);
        statement.setLong(1, number);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong("id");
        }
//        } catch (Exception e) {
//            log.error("getBankAccountIdByNumber()", e);
//        } finally {
//            if (connectionPool != null) {
//                try {
//                    connectionPool.closeConnection(connection);
//                } catch (Exception e) {
//                    log.error("Exception (getBankAccountIdByNumber(),connectionPool.closeConnection()): ", e);
//                }
//            }
//        }
        return id;
    }

    private String createQuery(Bank sendingBank) {

        return "select id from " + sendingBank.getName().toLowerCase().replace("-", "_") + "_accounts where number = ?";
    }
}
