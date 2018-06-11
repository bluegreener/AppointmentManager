package com.dwhi219.c195.dao;

import com.dwhi219.c195.util.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Factory class for managing database connections
 *
 * @author Daniel White
 */
public class DbConnectionManager {

    private Connection connection;
    private static DbConnectionManager instance;

    private DbConnectionManager() {
        try {
            Class.forName(Constants.JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static DbConnectionManager getInstance() {
        if (instance == null) {
            instance = new DbConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DBUSER, Constants.DBPASS);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
