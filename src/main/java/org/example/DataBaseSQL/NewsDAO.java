package org.example.DataBaseSQL;

import org.example.Cache.NewsCache;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {

    private static final String INSERT_NEWS_SQL = "INSERT INTO news (title, url, summary, time_published, source) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_NEWS_SQL = "SELECT * FROM news";

    private final NewsCache newsCache = new NewsCache(); // Internal cache

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

            // Cache the news item
            List<News> cachedNewsList = newsCache.get("topNews");
            if (cachedNewsList == null) {
                cachedNewsList = new ArrayList<>();
            }
            cachedNewsList.add(new News(title, url, summary, timePublished, source));

            // Store only the top 10 news in the cache
            if (cachedNewsList.size() > 10) {
                cachedNewsList.remove(0); // Remove the oldest news
            }
            newsCache.put("topNews", cachedNewsList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();

        // Check if top news is in cache
        if (newsCache.containsKey("topNews")) {
            return newsCache.get("topNews"); // Return cached news if available
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_NEWS_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String url = rs.getString("url");
                String summary = rs.getString("summary");
                String timePublished = rs.getString("time_published");
                String source = rs.getString("source");

                News news = new News(title, url, summary, timePublished, source);
                newsList.add(news);
            }

            // Cache the top 10 news
            newsCache.put("topNews", newsList.size() > 10 ? newsList.subList(0, 10) : newsList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public List<News> getCachedNews() {
        return newsCache.get("topNews"); // Return the cached news list
    }

}
