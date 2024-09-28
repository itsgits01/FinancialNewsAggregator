package org.example.Database;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.example.APIService.AlphaVantageAPI;

public class DatabaseRecords {
    private static final String URL = "jdbc:mysql://localhost:3306/financialNewsAggregator";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Root";
    // Generate comment for all the instance variables

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    private static void initializeConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
    }

    public static void main(String[] args) {
        try {
            initializeConnection();
            // connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
            List<NewsArticle> newsArticles = alphaVantageAPI.fetchNews();

            for (NewsArticle article : newsArticles) {
                createRecord(article);
            }

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

    private static void createRecord(NewsArticle article) {
        String checkQuery = "SELECT COUNT(*) FROM news_articles WHERE title = ?";
        String query = "INSERT INTO news_articles (title, url, summary, timePublished, source) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setString(1, article.getTitle());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return; // Skipping insertion if a duplicate is found
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getUrl());
            preparedStatement.setString(3, article.getSummary());
            preparedStatement.setString(4, article.getTimePublished());
            preparedStatement.setString(5, article.getSource());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<NewsArticle> readRecord() {
        String query = "SELECT * FROM news_articles";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            List<NewsArticle> readNewsArticles = new ArrayList<>();
            while (resultSet.next()) {
                NewsArticle readNewsArticle = new NewsArticle(
                        resultSet.getString("title"),
                        resultSet.getString("url"),
                        resultSet.getString("summary"),
                        resultSet.getString("timePublished"),
                        resultSet.getString("source"));
                readNewsArticles.add(readNewsArticle);
            }
            return readNewsArticles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void storeInDatabase(List<NewsArticle> articles) {
        for (NewsArticle article : articles) {
            createRecord(article); // Reuse your existing createRecord method
        }
    }

    // private static void updateRecord() {
    // String query = "UPDATE employees SET salary = ? WHERE EmployeeID = ?";
    // }

    // private static void deleteRecord() {
    // String query = "DELETE FROM employees WHERE EmployeeID = ?";
    // }
}
