package by.nortin.repository.impl;

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
            log.error("Exception getAll()", e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error("Exception checkAuthentication().connectionPool.closeConnection(): ", e);
                }
            }
        }
        return banks;
    }
}
