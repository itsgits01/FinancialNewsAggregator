package org.example.DataBaseSQL;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        NewsDAO newsDAO = new NewsDAO();

        // Fetch and print all news from the database
        List<News> newsList = newsDAO.getAllNews();

        // Print news from the database
//        System.out.println("News fetched from database:");
//        for (News news : newsList) {
//            System.out.println(news);
//        }
//
//        // Print total news fetched from the database
//        System.out.println("Total news fetched: " + newsList.size());

        // Show cached news
        System.out.println("\n--------------------------Top news around the Globe------------------:");
        List<News> cachedNewsList = newsDAO.getCachedNews();
        if (cachedNewsList != null) {
            for (News cachedNews : cachedNewsList) {
                System.out.println(cachedNews);
            }
        } else {
            System.out.println("No cached news available.");
        }
    }
}
