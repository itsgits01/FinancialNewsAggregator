package org.example.cache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewsCache {
    


    private void startCacheCleaner() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            long currentTimeMillis = System.currentTimeMillis();
            cacheMap.entrySet().removeIf(entry -> isCacheExpired(entry.getValue()));
        }, 1, 1, TimeUnit.MINUTES);
    }


    private static class CacheItem {
        private final NewsArticle article;
        private final long timestamp; // Timestamp of when the item was added to the cache

        public CacheItem(NewsArticle article, long timestamp) {
            this.article = article;
            this.timestamp = timestamp;
        }
    }
}
