package org.example;

import java.sql.*;

public class DatabaseRecords {
    private static final String URL = "jdbc:mysql://localhost:3306/financialNewsAggregator";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Root";
    // Generate comment for all the instance variables

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // createRecord();
            readRecord();
            // updateRecord();
            // deleteRecord();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createRecord() {
        ;
    }

    private static void readRecord() {
        String query = "SELECT * FROM employees";
    }

    private static void updateRecord() {
        String query = "UPDATE employees SET salary = ? WHERE EmployeeID = ?";
    }

    private static void deleteRecord() {
        String query = "DELETE FROM employees WHERE EmployeeID = ?";
    }
}
