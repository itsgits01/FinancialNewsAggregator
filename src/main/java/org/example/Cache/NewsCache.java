package org.example.Cache;

import org.example.DataBaseSQL.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsCache {
    private final Map<String, List<News>> cache = new HashMap<>();

    public void put(String key, List<News> newsList) {
        cache.put(key, newsList);
    }

    public List<News> get(String key) {
        return cache.get(key);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
}
