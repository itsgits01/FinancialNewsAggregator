package org.example.Cache;

import org.example.Database.DatabaseRecords;
import org.example.Database.NewsArticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsCache {
    private final Map<String, List<NewsArticle>> cache = new HashMap<>();

    public void put(String key, List<NewsArticle> newsList) {
        cache.put(key, newsList);
    }

    public List<NewsArticle> get(String key) {
        return cache.get(key);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
