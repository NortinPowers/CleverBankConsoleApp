package by.nortin.repository;

import static by.nortin.util.MessageUtils.getErrorMessageToLog;

import by.nortin.config.AppConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class ConnectionPool {

    private static volatile ConnectionPool instance;
    private static final AppConfig APP_CONFIG;
    private static final int MAX_CONNECTION_COUNT;
    private static final int MIN_CONNECTION_COUNT;

    private static final String URL;
    private static final String LOGIN;
    private static final String PASS;

    static {
        APP_CONFIG = new AppConfig();
        Map<String, Object> databaseProperties = APP_CONFIG.getProperty("database");
        URL = (String) databaseProperties.get("url");
        LOGIN = (String) databaseProperties.get("username");
        PASS = (String) databaseProperties.get("password");
        MAX_CONNECTION_COUNT = (Integer) databaseProperties.get("max-connection");
        MIN_CONNECTION_COUNT = (Integer) databaseProperties.get("min-connection");
    }

    private final AtomicInteger currentConnectionNumber = new AtomicInteger(MIN_CONNECTION_COUNT);
    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(MAX_CONNECTION_COUNT, true);

    /**
     * The method creates a connection pool of connection.
     */
    private ConnectionPool() {
        for (int i = 0; i < MIN_CONNECTION_COUNT; i++) {
            try {
                pool.add(DriverManager.getConnection(URL, LOGIN, PASS));
            } catch (SQLException e) {
                log.error(getErrorMessageToLog("ConnectionPool()"), e);
            }
        }
    }

    /**
     * The method creates additional connections.
     *
     * @throws Exception error adding connection
     */
    private void openAdditionalConnection() throws Exception {
        try {
            pool.add(DriverManager.getConnection(URL, LOGIN, PASS));
            currentConnectionNumber.incrementAndGet();
        } catch (SQLException e) {
            throw new Exception("New connection wasn't add in the connection pool", e);
        }
    }

    /**
     * The method returns the instance of connection pool.
     *
     * @return instance of ConnectionPool
     */
    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * The method returns connections from the connection pool.
     *
     * @return Connection
     * @throws Exception the maximum number of connections has been reached
     */
    public Connection getConnection() throws Exception {
        Connection connection;
        try {
            if (pool.isEmpty() && currentConnectionNumber.get() < MAX_CONNECTION_COUNT) {
                openAdditionalConnection();
            }
            connection = pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception("Max count of connections was reached!", e);
        }

        return connection;
    }

    /**
     * The method closes the connection and returns it to the connection pool.
     *
     * @param connection Connection
     * @throws Exception it is possible to return the connections to the connection pool
     */
    public void closeConnection(Connection connection) throws Exception {
        if (connection != null) {
            if (currentConnectionNumber.get() > MIN_CONNECTION_COUNT) {
                currentConnectionNumber.decrementAndGet();
            }
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new Exception("Connection wasn't returned into pool properly");
            }
        }
    }

    /**
     * The method closes all active connections.
     */
    public void closeAllConnection() {
        for (Connection connection : pool) {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                log.error(getErrorMessageToLog("Some connection cannot be closed"), e);
            }
        }
    }
}
