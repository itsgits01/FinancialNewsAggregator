package org.example;

/**
 * Hello world!
 *
 */

import org.example.Service.NewsAPIService;
import org.example.Database.NewsArticle;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        NewsAPIService newsAPIService = new NewsAPIService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Financial News Aggregator!");
        while (true) {
            System.out.println("Please choose an option:");
            System.out.println("1. Fetch Latest News");
            System.out.println("2. Read Cached News");
            System.out.println("3. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    // Fetch news and display it
                    fetchLatestNews(newsAPIService);
                    break;
                case "2":
                    // Display cached news
                    readCachedNews(newsAPIService);
                    break;
                case "3":
                    // Exit the program
                    System.out.println("Exiting the application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void fetchLatestNews(NewsAPIService newsAPIService) {
        System.out.println("Fetching latest financial news...");
        List<NewsArticle> newsArticles = newsAPIService.getNewsArticles();
        if (!newsArticles.isEmpty()) {
            for (NewsArticle article : newsArticles) {
                displayArticle(article);
            }
        } else {
            System.out.println("No news articles found.");
        }
    }

    private static void readCachedNews(NewsAPIService newsAPIService) {
        System.out.println("Displaying cached news...");
        List<NewsArticle> cachedArticles = newsAPIService.getNewsArticles(); // Reuse the method to fetch cached news
        if (!cachedArticles.isEmpty()) {
            for (NewsArticle article : cachedArticles) {
                displayArticle(article);
            }
        } else {
            System.out.println("No cached news articles found.");
        }
    }

    private static void displayArticle(NewsArticle article) {
        System.out.println("----------------------------------------");
        System.out.println("Title: " + article.getTitle());
        System.out.println("Summary: " + article.getSummary());
        System.out.println("URL: " + article.getUrl());
        System.out.println("Published At: " + article.getTimePublished());
        System.out.println("Source: " + article.getSource());
        System.out.println("----------------------------------------");
    }
}
