package by.nortin.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class ConnectionPool {

    private static volatile ConnectionPool instance;

    private static final String DB_PROPERTY_FILE = "application.yaml";
    private static final String DB_URL = "database.url";
    private static final String DB_LOGIN = "database.username";
    private static final String DB_PASS = "database.password";
    private static final int MAX_CONNECTION_COUNT = 20;
    private static final int MIN_CONNECTION_COUNT = 10;

    private static final String URL;
    private static final String LOGIN;
    private static final String PASS;

    static {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY_FILE, Locale.getDefault());
//        URL = resourceBundle.getString(DB_URL);
//        LOGIN = resourceBundle.getString(DB_LOGIN);
//        PASS = resourceBundle.getString(DB_PASS//
        URL = "jdbc:postgresql://localhost:5432/clever_bank";
        LOGIN = "postgres";
        PASS = "root";

    }

    private final AtomicInteger currentConnectionNumber = new AtomicInteger(MIN_CONNECTION_COUNT);
    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(MAX_CONNECTION_COUNT, true);

    private ConnectionPool() {
        for (int i = 0; i < MIN_CONNECTION_COUNT; i++) {
            try {
                pool.add(DriverManager.getConnection(URL, LOGIN, PASS));
            } catch (SQLException e) {
                System.out.println("SQLException ConnectionPool(): " + e.getMessage());
            }
        }
    }

    private void openAdditionalConnection() throws Exception {
        try {
            pool.add(DriverManager.getConnection(URL, LOGIN, PASS));
            currentConnectionNumber.incrementAndGet();
        } catch (SQLException e) {
            throw new Exception("New connection wasn't add in the connection pool", e);
        }
    }

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

    public Connection getConnection() throws Exception {
        Connection connection;
        try {
            if (pool.isEmpty() && currentConnectionNumber.get() < MAX_CONNECTION_COUNT) {
                openAdditionalConnection();
            }
            connection = pool.take();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new Exception("Max count of connections was reached!");
        }

        return connection;
    }

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

    public void closeAllConnection() {
        for (Connection connection : pool) {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println("Some connection cannot be closed");
            }
        }
    }
}