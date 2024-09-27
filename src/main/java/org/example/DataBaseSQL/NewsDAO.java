package org.example.DataBaseSQL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewsDAO {

    private static final String INSERT_NEWS_SQL = "INSERT INTO news (title, url, summary, time_published, source) VALUES (?, ?, ?, ?, ?)";

    public void saveNews(String title, String url, String summary, String timePublished, String source) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_NEWS_SQL)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, url);
            preparedStatement.setString(3, summary);
            preparedStatement.setString(4, timePublished);
            preparedStatement.setString(5, source);

            // Execute the update
            preparedStatement.executeUpdate();
            System.out.println("News saved to database!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Implement other CRUD methods (Update, Delete, Read) as needed
}
