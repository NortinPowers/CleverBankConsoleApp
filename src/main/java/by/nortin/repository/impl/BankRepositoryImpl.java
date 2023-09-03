package by.nortin.repository.impl;

import static by.nortin.util.Constants.MethodName.CLOSE_CONNECTION;
import static by.nortin.util.Constants.MethodName.GET_ALL;
import static by.nortin.util.MessageUtils.getErrorMessageToLog;

import by.nortin.model.Bank;
import by.nortin.repository.BankRepository;
import by.nortin.repository.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BankRepositoryImpl implements BankRepository {

    private static final String GET_ALL_BANKS = "select * from banks";
    private final ConnectionPool connectionPool;
    private Connection connection;

    {
        connectionPool = ConnectionPool.getInstance();
        connection = null;
    }

    /**
     * Implementation of the method returns a list of all banks.
     *
     * @return List of Bank
     */
    @Override
    public List<Bank> getAll() {
        List<Bank> banks = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BANKS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Bank bank = new Bank();
                bank.setId(id);
                bank.setName(name);
                banks.add(bank);
            }
        } catch (Exception e) {
            log.error(getErrorMessageToLog(GET_ALL), e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(GET_ALL, CLOSE_CONNECTION), e);
                }
            }
        }
        return banks;
    }
}
