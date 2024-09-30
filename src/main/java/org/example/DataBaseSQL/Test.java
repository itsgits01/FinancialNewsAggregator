package org.example.DataBaseSQL;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        NewsDAO newsDAO = new NewsDAO();

        // Measure time to fetch first 10 news from the database
        long startTimeDB = System.nanoTime();
        List<News> newsListDB = newsDAO.getNewsFromDB(10, 0); // Fetch first 10 news from DB
        long endTimeDB = System.nanoTime();
        long timeTakenDB = endTimeDB - startTimeDB;

        // Print news from the database
        System.out.println("News fetched from database:");
        for (News news : newsListDB) {
            System.out.println(news);
        }

        // Print total news fetched from the database
        System.out.println("Total news fetched from database: " + newsListDB.size());
        System.out.println("Time taken to fetch news from database: " + timeTakenDB + " nanoseconds");

        // Measure time to fetch news from L2 cache (after fetching from the database)
        long startTimeL2 = System.nanoTime();
        List<News> newsListL2 = newsDAO.getNews(10, 0); // Fetch from L2 cache
        long endTimeL2 = System.nanoTime();
        long timeTakenL2 = endTimeL2 - startTimeL2;

        // Print news fetched from L2 cache
        System.out.println("\nNews fetched from L2 cache:");
        for (News news : newsListL2) {
            System.out.println(news);
        }

        System.out.println("Total news fetched from L2 cache: " + newsListL2.size());
        System.out.println("Time taken to fetch news from L2 cache: " + timeTakenL2 + " nanoseconds");

        // Measure time to fetch news from L1 cache (after fetching from the L2 cache)
        long startTimeL1 = System.nanoTime();
        List<News> newsListL1 = newsDAO.getNews(100, 0); // Fetch from L1 cache
        long endTimeL1 = System.nanoTime();
        long timeTakenL1 = endTimeL1 - startTimeL1;

        // Print news fetched from L1 cache
        System.out.println("\nNews fetched from L1 cache:");
        for (News news : newsListL1) {
            System.out.println(news);
        }

        System.out.println("Total news fetched from L1 cache: " + newsListL1.size());
        System.out.println("Time taken to fetch news from L1 cache: " + timeTakenL1 + " nanoseconds");

        // Calculate and print the percentage decrease in time compared to the database
        if (timeTakenDB > 0) {
            double percentageDecreaseL2 = ((double) (timeTakenDB - timeTakenL2) / timeTakenDB) * 100;
            double percentageDecreaseL1 = ((double) (timeTakenDB - timeTakenL1) / timeTakenDB) * 100;

            System.out.println("\nPercentage decrease in time compared to database retrieval (L2 cache): " + percentageDecreaseL2 + " %");
            System.out.println("Percentage decrease in time compared to database retrieval (L1 cache): " + percentageDecreaseL1 + " %");
        } else {
            System.out.println("Unable to calculate percentage decrease as database retrieval time is zero.");
        }

        // Calculate and print the percentage decrease between L2 and L1 cache retrieval times
        if (timeTakenL2 > 0) {
            double percentageDecreaseBetweenL1AndL2 = ((double) (timeTakenL2 - timeTakenL1) / timeTakenL2) * 100;
            System.out.println("Percentage decrease in time compared to L2 cache retrieval (L1 cache): " + percentageDecreaseBetweenL1AndL2 + " %");
        } else {
            System.out.println("Unable to calculate percentage decrease between L2 and L1 cache retrieval as L2 cache retrieval time is zero.");
        }

        // Print all retrieval times for comparison
        System.out.println("\n----------------- Comparison of Retrieval Times -----------------");
        System.out.println("Time taken to fetch from L1 cache: " + timeTakenL1 + " nanoseconds");
        System.out.println("Time taken to fetch from L2 cache: " + timeTakenL2 + " nanoseconds");
        System.out.println("Time taken to fetch from database: " + timeTakenDB + " nanoseconds");
    }
}
