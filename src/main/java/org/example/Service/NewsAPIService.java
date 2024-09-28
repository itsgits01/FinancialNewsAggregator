package org.example.Service;

import org.example.Cache.NewsCache;
import org.example.Database.DatabaseRecords;
import org.example.Database.NewsArticle;
import org.example.APIService.AlphaVantageAPI;
import java.util.List;

public class NewsAPIService {
    private NewsCache cache;
    private DatabaseRecords dbRecords;
    private AlphaVantageAPI api;

    public NewsAPIService() {
        this.cache = new NewsCache();
        this.dbRecords = new DatabaseRecords();
        this.api = new AlphaVantageAPI();
    }

    // Method to get news articles
    public List<NewsArticle> getNewsArticles() {
        List<NewsArticle> cachedNews = NewsCache.getCachedNews();

        // Step 1: Check cache first
        if (!cachedNews.isEmpty()) {
            System.out.println("News fetched from cache.");
            return cachedNews;
        }

        // Step 2: If cache is empty, check database
        List<NewsArticle> dbNews = DatabaseRecords.readRecord();
        if (dbNews != null && !dbNews.isEmpty()) {
            System.out.println("News fetched from database.");
            NewsCache.storeInCache(dbNews); // Store fetched database news in cache
            return dbNews;
        }

        // Step 3: If both cache and database are empty, fetch from the API
        List<NewsArticle> apiNews = api.fetchNews();
        if (!apiNews.isEmpty()) {
            System.out.println("News fetched from API.");
            NewsCache.storeInCache(apiNews); // Store fetched API news in cache
            dbRecords.storeInDatabase(apiNews); // Store API news in the database
            return apiNews;
        }

        System.out.println("No news available.");
        return apiNews; // Returns an empty list if no data is available
    }
}
