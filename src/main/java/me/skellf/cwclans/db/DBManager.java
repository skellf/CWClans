package me.skellf.cwclans.db;

import me.skellf.cwclans.CWClans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBManager {

    private Connection connection;
    private final Logger log;
    private final String path = CWClans.getInstance().getDataFolder() + "/clans.db";

    public DBManager(Logger log) {
        this.log = log;
    }

    public void setupDB() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);

            try (PreparedStatement statement = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS clans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    leader TEXT NOT NULL,
                    members TEXT NOT NULL,
                    coins INTEGER DEFAULT 0,
                    );
                    """
            )) {
                log.info("Setting up database!");
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            log.severe("Error setting up a database! Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

}
