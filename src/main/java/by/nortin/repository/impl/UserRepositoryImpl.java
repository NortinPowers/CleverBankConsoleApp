package by.nortin.repository.impl;

import by.nortin.model.User;
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
//    public boolean checkAuthentication(String login, String password) {
    public boolean checkAuthentication(User user) {
        boolean isPresent = false;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, user.getLogin());
//            statement.setString(1, login);
            statement.setString(2, user.getPassword());
//            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isPresent = true;
            }
        } catch (Exception e) {
            log.error("checkAuthentication", e);
        } finally {
            if (connectionPool != null) {
                try {
                    connectionPool.closeConnection(connection);
                } catch (Exception e) {
                    log.error("Exception (checkAuthentication(),connectionPool.closeConnection()): ", e);
                }
            }
        }
        return isPresent;
    }
}
