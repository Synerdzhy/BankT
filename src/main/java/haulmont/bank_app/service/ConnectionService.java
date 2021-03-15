package haulmont.bank_app.service;

import java.sql.*;

public class ConnectionService {
    private final Connection connection;
    private static ConnectionService connectionService;

    private ConnectionService() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        this.connection = DriverManager.getConnection("jdbc:hsqldb:file:bankdb\\base", "admin", "");
    }

    public static ConnectionService getInstance() {
        if (connectionService == null) {
            try {
                connectionService = new ConnectionService();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connectionService;
    }

    public Connection getConnection() {
        return connection;
    }
}
