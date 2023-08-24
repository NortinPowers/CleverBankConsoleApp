package by.nortin.repository.impl;

import by.nortin.repository.ConnectionPool;
import by.nortin.repository.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRepositoryImpl implements UserRepository {

    private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "select * from users where login=? and password=?";
    private final ConnectionPool connectionPool;

    {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public boolean checkAuthentication(String login, String password) {
        boolean isPresent = false;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isPresent = true;
            }
        } catch (Exception e) {
            log.error("checkAuthentication" + e.getMessage());
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error("Exception (checkAuthentication.connectionPool): " + e.getMessage());
                }
            }
        }
        return isPresent;
    }
}
