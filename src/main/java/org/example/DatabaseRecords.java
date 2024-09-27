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

    // following is my create table statement
    /*
     * create table employees(
     * id int auto_increment primary key,
     * first_name varchar(50),
     * last_name varchar(50),
     * email varchar(100),
     * hire_date DATE,
     * salary decimal(10,2)
     * );
     */

    private static void createRecord() {
        String query = "INSERT INTO Employees (EmployeeID, name, email, phone,JobTitle, salary,Department) VALUES (?, ?, ?, ?, ?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Rithwik");
            preparedStatement.setString(3, "rithwik@gmail.com");
            preparedStatement.setString(4, "9876543210");
            preparedStatement.setString(5, "Software Engineer");
            preparedStatement.setDouble(6, 50000.00);
            preparedStatement.setString(7, "Software Development");
            preparedStatement.executeUpdate();
            System.out.println("Record created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readRecord() {
        String query = "SELECT * FROM employees";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("EmployeeID") + " " + resultSet.getString("name") + " "
                        + resultSet.getString("email") + " " + resultSet.getString("phone") + " "
                        + resultSet.getString("JobTitle") + " " + resultSet.getString("salary") + " "
                        + resultSet.getString("Department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateRecord() {
        String query = "UPDATE employees SET salary = ? WHERE EmployeeID = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, 60000.00);
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
            System.out.println("Record updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteRecord() {
        String query = "DELETE FROM employees WHERE EmployeeID = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.executeUpdate();
            System.out.println("Record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
