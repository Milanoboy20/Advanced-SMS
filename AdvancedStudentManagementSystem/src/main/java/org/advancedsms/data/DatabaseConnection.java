package org.advancedsms.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton class for database connection
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;


    private DatabaseConnection() {
        try {
           //db url, using h2 in memory database
            String db_url = "jdbc:h2:mem:";

            //create connection
            this.connection = DriverManager.getConnection(db_url);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //public method to get single instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    //since try with resources was not used, have to close connection when done with db operations
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("DB connection closed successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
