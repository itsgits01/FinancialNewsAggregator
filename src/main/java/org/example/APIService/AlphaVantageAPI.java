package org.example.APIService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.example.DataBaseSQL.NewsDAO;

import java.io.IOException;

public class AlphaVantageAPI {

    private static final String API_KEY = "IIG98SZEVNYQD9CJ";  // Replace with your API Key
    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";
    private static final NewsDAO newsDAO = new NewsDAO();  // Instantiate the DAO for database operations

    public static void main(String[] args) {
        try {
            // Create HttpClient instance
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // API endpoint for fetching news
            String function = "NEWS_SENTIMENT";
            // API endpoint for fetching news with sorting and limit parameters
            String sort = "LATEST";
            int limit = 1000;
            String url = String.format("%s?function=%s&apikey=%s&sort=%s&limit=%d", ALPHA_VANTAGE_URL, function, API_KEY, sort, limit);

            //String url = String.format("%s?function=%s&apikey=%s", ALPHA_VANTAGE_URL, function, API_KEY);

            // Create the HTTP GET request
            HttpGet request = new HttpGet(url);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                // Parse the JSON response and extract required fields
                extractNewsData(jsonResponse);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to parse the JSON response and extract specific fields
    // Method to parse the JSON response and extract specific fields
    private static void extractNewsData(String jsonResponse) {
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON into a JsonNode tree
            JsonNode root = objectMapper.readTree(jsonResponse);

            // Iterate over the articles and extract required fields
            JsonNode newsArray = root.get("feed");
            if (newsArray != null && newsArray.isArray()) {
                int itemCount = 0; // Counter to track the number of items fetched successfully
                for (JsonNode newsItem : newsArray) {
                    String title = newsItem.get("title").asText();
                    String url = newsItem.get("url").asText();
                    String summary = newsItem.get("summary").asText();
                    String timePublished = newsItem.get("time_published").asText();
                    String source = newsItem.get("source").asText();

                    // Print extracted values
                    System.out.println("Title: " + title);
                    System.out.println("URL: " + url);
//                    System.out.println("Summary: " + summary);
//                    System.out.println("Time Published: " + timePublished);
//                    System.out.println("Source: " + source);
                    System.out.println("-----------------------------");

                    // Save each news item to the database
                    newsDAO.saveNewsToDB(title, url, summary, timePublished, source);

                    // Increment the counter for each successfully processed item
                    itemCount++;
                }

                // Print the total number of items fetched successfully
                System.out.println("Total number of news items fetched: " + itemCount);
            } else {
                System.out.println("No news data available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
