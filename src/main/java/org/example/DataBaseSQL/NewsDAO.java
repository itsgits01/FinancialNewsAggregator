package org.example.DataBaseSQL;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.example.Cache.NewsCache;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NewsDAO {

    private static final String INSERT_NEWS_SQL = "INSERT INTO news (title, url, summary, time_published, source) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_NEWS_SQL = "SELECT * FROM news LIMIT ? OFFSET ?";

    private static final int L1_CACHE_SIZE = 10;
    private static final int L2_CACHE_SIZE = 100;
    private static final int L2_CACHE_DURATION_MINUTES = 30;

    // L1 Cache - Fastest access
    private final Map<String, List<News>> l1Cache = new LinkedHashMap<String, List<News>>(L1_CACHE_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, List<News>> eldest) {
            return size() > L1_CACHE_SIZE;
        }
    };

    // L2 Cache - Larger, slightly slower
    private final Cache<String, List<News>> l2Cache = CacheBuilder.newBuilder()
            .maximumSize(L2_CACHE_SIZE)
            .expireAfterAccess(L2_CACHE_DURATION_MINUTES, TimeUnit.MINUTES)
            .build();

    public void saveNewsToDB(String title, String url, String summary, String timePublished, String source) {
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

            // Update L1 and L2 caches
            News news = new News(title, url, summary, timePublished, source);
            updateCaches(news);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCaches(News news) {
        // Update L1 Cache
        List<News> l1NewsList = l1Cache.get("topNews");
        if (l1NewsList == null) {
            l1NewsList = new ArrayList<>();
        }
        l1NewsList.add(news);
        if (l1NewsList.size() > L1_CACHE_SIZE) {
            l1NewsList.remove(0);
        }
        l1Cache.put("topNews", l1NewsList);

        // Update L2 Cache
        List<News> l2NewsList = l2Cache.getIfPresent("topNews");
        if (l2NewsList == null) {
            l2NewsList = new ArrayList<>();
        }
        l2NewsList.add(news);
        if (l2NewsList.size() > L2_CACHE_SIZE) {
            l2NewsList.remove(0);
        }
        l2Cache.put("topNews", l2NewsList);
    }

    public List<News> getNews(int limit, int offset) {
        // Try to get news from L1 Cache
        List<News> newsList = l1Cache.get("topNews");
        if (newsList != null && newsList.size() > offset) {
            return paginate(newsList, limit, offset);
        }

        // Try to get news from L2 Cache
        newsList = l2Cache.getIfPresent("topNews");
        if (newsList != null && newsList.size() > offset) {
            return paginate(newsList, limit, offset);
        }

        // If not in caches, fetch from database
        return getNewsFromDB(limit, offset);
    }

    public List<News> getNewsFromDB(int limit, int offset) {
        List<News> newsList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_NEWS_SQL)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String url = rs.getString("url");
                    String summary = rs.getString("summary");
                    String timePublished = rs.getString("time_published");
                    String source = rs.getString("source");

                    News news = new News(title, url, summary, timePublished, source);
                    newsList.add(news);
                }

                // Update L2 cache with the latest data fetched from DB
                l2Cache.put("topNews", newsList);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    private List<News> paginate(List<News> newsList, int limit, int offset) {
        int toIndex = Math.min(newsList.size(), offset + limit);
        if (offset >= newsList.size()) {
            return new ArrayList<>();
        }
        return newsList.subList(offset, toIndex);
    }

    public List<News> getCachedNews() {
        return l1Cache.get("topNews"); // Return the cached news list from L1 cache
    }
}
