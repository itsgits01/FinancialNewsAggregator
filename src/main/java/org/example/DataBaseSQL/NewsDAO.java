package org.example.DataBaseSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {

    private static final String INSERT_NEWS_SQL = "INSERT INTO news (title, url, summary, time_published, source) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_NEWS_SQL = "SELECT * FROM news";

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

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_NEWS_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String url = rs.getString("url");
                String summary = rs.getString("summary");
                String timePublished = rs.getString("time_published");
                String source = rs.getString("source");

                // Create a new News object and add it to the list
                News news = new News(title, url, summary, timePublished, source);
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
