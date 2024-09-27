package org.example.APIService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

// public class AlphaVantageAPI {

//     private static final String API_KEY = "IIG98SZEVNYQD9CJ";  // Replace with your API Key
//     private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";

//     public static void main(String[] args) {
//         try {
//             // Create HttpClient instance
//             CloseableHttpClient httpClient = HttpClients.createDefault();

//             // API endpoint for fetching news
//             String function = "NEWS_SENTIMENT";
//             String url = String.format("%s?function=%s&apikey=%s", ALPHA_VANTAGE_URL, function, API_KEY);

//             // Create the HTTP GET request
//             HttpGet request = new HttpGet(url);

//             // Execute the request
//             try (CloseableHttpResponse response = httpClient.execute(request)) {
//                 String jsonResponse = EntityUtils.toString(response.getEntity());

//                 // Parse the JSON response and extract required fields
//                 extractNewsData(jsonResponse);

//             } catch (ParseException e) {
//                 throw new RuntimeException(e);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     // Method to parse the JSON response and extract specific fields
//     private static void extractNewsData(String jsonResponse) {
//         try {
//             // Create ObjectMapper instance
//             ObjectMapper objectMapper = new ObjectMapper();

//             // Parse the JSON into a JsonNode tree
//             JsonNode root = objectMapper.readTree(jsonResponse);

//             // Iterate over the articles and extract required fields
//             JsonNode newsArray = root.get("feed");
//             if (newsArray != null && newsArray.isArray()) {
//                 for (JsonNode newsItem : newsArray) {
//                     String title = newsItem.get("title").asText();
//                     String url = newsItem.get("url").asText();
//                     String summary = newsItem.get("summary").asText();
//                     String timePublished = newsItem.get("time_published").asText();
//                     String source = newsItem.get("source").asText();

//                     // Print extracted values
//                     System.out.println("Title: " + title);
//                     System.out.println("URL: " + url);
//                     System.out.println("Summary: " + summary);
//                     System.out.println("Time Published: " + timePublished);
//                     System.out.println("Source: " + source);
//                     System.out.println("-----------------------------");
//                 }
//             } else {
//                 System.out.println("No news data available.");
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

import java.util.ArrayList;
import java.util.List;
import org.example.Database.NewsArticle;

public class AlphaVantageAPI {
    private static final String API_KEY = "IIG98SZEVNYQD9CJ";
    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";

    public List<NewsArticle> fetchNews() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String function = "NEWS_SENTIMENT";
            String url = String.format("%s?function=%s&apikey=%s", ALPHA_VANTAGE_URL, function, API_KEY);

            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                articles = extractNewsData(jsonResponse);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public List<NewsArticle> extractNewsData(String jsonResponse) {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode newsArray = root.get("feed");

            if (newsArray != null && newsArray.isArray()) {
                for (JsonNode newsItem : newsArray) {
                    String title = newsItem.get("title").asText();
                    String url = newsItem.get("url").asText();
                    String summary = newsItem.get("summary").asText();
                    String timePublished = newsItem.get("time_published").asText();
                    String source = newsItem.get("source").asText();

                    articles.add(new NewsArticle(title, url, summary, timePublished, source));
                    System.out.println("Tile" + "-" + title);
                    System.out.println("URL" + "-" + url);
                    System.out.println("Summary" + "-" + summary);
                    System.out.println("Time Published" + "-" + timePublished);
                    System.out.println("Source" + "-" + source);
                }
            } else {
                System.out.println("No news data available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public static void main(String[] args) {
        AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
        List<NewsArticle> newsArticles = alphaVantageAPI.fetchNews();
        // for (NewsArticle article : newsArticles) {
        // System.out.println(article.getTitle());
        // }
    }
}