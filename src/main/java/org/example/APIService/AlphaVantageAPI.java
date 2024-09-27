package org.example.APIService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
<<<<<<< HEAD
=======
import org.example.DataBaseSQL.NewsDAO;
>>>>>>> gitesh

import java.io.IOException;

public class AlphaVantageAPI {

    private static final String API_KEY = "IIG98SZEVNYQD9CJ";  // Replace with your API Key
    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";
<<<<<<< HEAD
=======
    private static final NewsDAO newsDAO = new NewsDAO();  // Instantiate the DAO for database operations
>>>>>>> gitesh

    public static void main(String[] args) {
        try {
            // Create HttpClient instance
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // API endpoint for fetching news
            String function = "NEWS_SENTIMENT";
            String url = String.format("%s?function=%s&apikey=%s", ALPHA_VANTAGE_URL, function, API_KEY);

            // Create the HTTP GET request
            HttpGet request = new HttpGet(url);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                // Parse the JSON response and extract required fields
<<<<<<< HEAD
                extractNewsData(jsonResponse,100);
=======
                extractNewsData(jsonResponse);
>>>>>>> gitesh

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to parse the JSON response and extract specific fields
<<<<<<< HEAD
    private static void extractNewsData(String jsonResponse, int limit) {
=======
    private static void extractNewsData(String jsonResponse) {
>>>>>>> gitesh
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON into a JsonNode tree
            JsonNode root = objectMapper.readTree(jsonResponse);

            // Iterate over the articles and extract required fields
            JsonNode newsArray = root.get("feed");
            if (newsArray != null && newsArray.isArray()) {
<<<<<<< HEAD
                int count = 0;  // To track the number of results
                for (JsonNode newsItem : newsArray) {
                    if (count >= limit) {
                        break;  // Stop when the limit is reached
                    }

=======
                for (JsonNode newsItem : newsArray) {
>>>>>>> gitesh
                    String title = newsItem.get("title").asText();
                    String url = newsItem.get("url").asText();
                    String summary = newsItem.get("summary").asText();
                    String timePublished = newsItem.get("time_published").asText();
                    String source = newsItem.get("source").asText();

                    // Print extracted values
                    System.out.println("Title: " + title);
                    System.out.println("URL: " + url);
                    System.out.println("Summary: " + summary);
                    System.out.println("Time Published: " + timePublished);
                    System.out.println("Source: " + source);
                    System.out.println("-----------------------------");

<<<<<<< HEAD
                    count++;  // Increment the counter
=======
                    // Save each news item to the database
                    newsDAO.saveNews(title, url, summary, timePublished, source);

>>>>>>> gitesh
                }
            } else {
                System.out.println("No news data available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD

=======
>>>>>>> gitesh
}
