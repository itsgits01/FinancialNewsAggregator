package org.example.Cache;

import org.example.Database.NewsArticle;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class NewsCache {
    private static final int CACHE_SIZE = 10;
    public static Map<String, NewsArticle> cache = new LinkedHashMap<String, NewsArticle>(CACHE_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, NewsArticle> eldest) {
            return size() > CACHE_SIZE;
        }
    };

    // Store a single article in the cache
    public static void addArticleToCache(NewsArticle article) {
        cache.put(article.getTitle(), article);
    }

    // Store multiple articles in the cache
    public static void storeInCache(List<NewsArticle> articles) {
        for (NewsArticle article : articles) {
            addArticleToCache(article);
        }
    }

    // Get a single article from the cache
    public static NewsArticle getArticleFromCache(String title) {
        return cache.get(title);
    }

    // Get all articles from the cache
    public static List<NewsArticle> getCachedNews() {
        return new ArrayList<>(cache.values());
    }

    // Check if an article is in the cache
    public static boolean isInCache(String title) {
        return cache.containsKey(title);
    }

    // Clear the entire cache
    public static void clearCache() {
        cache.clear();
    }
}
