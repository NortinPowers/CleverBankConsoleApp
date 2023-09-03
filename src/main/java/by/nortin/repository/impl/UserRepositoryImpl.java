package by.nortin.repository.impl;

import static by.nortin.util.Constants.MethodName.CHECK_AUTHENTICATION;
import static by.nortin.util.Constants.MethodName.CLOSE_CONNECTION;
import static by.nortin.util.MessageUtils.getErrorMessageToLog;

import by.nortin.model.User;
import by.nortin.repository.ConnectionPool;
import by.nortin.repository.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRepositoryImpl implements UserRepository {

    private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "select * from users where login=? and password = ?";
    private final ConnectionPool connectionPool;
    private Connection connection;

    {
        connectionPool = ConnectionPool.getInstance();
        connection = null;
    }

    /**
     * Implementation of the method verifies user authentication.
     *
     * @param user User
     * @return boolean result
     */
    @Override
    public boolean checkAuthentication(User user) {
        boolean isPresent = false;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isPresent = true;
            }
        } catch (Exception e) {
            log.error(getErrorMessageToLog(CHECK_AUTHENTICATION), e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error(getErrorMessageToLog(CHECK_AUTHENTICATION, CLOSE_CONNECTION), e);
                }
            }
        }
        return isPresent;
    }
}
