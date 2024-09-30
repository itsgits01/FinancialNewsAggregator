package org.example.Cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.example.DataBaseSQL.News;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewsCache {
    private static final int L1_CACHE_SIZE = 10;  // Adjust size as per requirement
    private static final int L2_CACHE_SIZE = 100; // Adjust size as per requirement
    private static final int L2_CACHE_DURATION_MINUTES = 30;  // Cache expiry time

    // L1 Cache (in-memory, LRU Cache)
    private final Map<String, List<News>> l1Cache = new LinkedHashMap<String, List<News>>(L1_CACHE_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, List<News>> eldest) {
            return size() > L1_CACHE_SIZE;
        }
    };

    // L2 Cache (using Guava Cache)
    private final Cache<String, List<News>> l2Cache = CacheBuilder.newBuilder()
            .maximumSize(L2_CACHE_SIZE)
            .expireAfterAccess(L2_CACHE_DURATION_MINUTES, TimeUnit.MINUTES)
            .build();

    public void put(String key, List<News> newsList) {
        l1Cache.put(key, newsList);
        l2Cache.put(key, newsList);
    }

    public List<News> get(String key) {
        // Try fetching from L1 cache first
        List<News> newsList = l1Cache.get(key);
        if (newsList != null) {
            return newsList;
        }

        // If not in L1 cache, try fetching from L2 cache
        newsList = l2Cache.getIfPresent(key);
        if (newsList != null) {
            // Move the news list to L1 cache for faster subsequent access
            l1Cache.put(key, newsList);
        }

        return newsList;
    }

    public boolean containsKey(String key) {
        return l1Cache.containsKey(key) || l2Cache.getIfPresent(key) != null;
    }
}
