package org.example.DataBaseSQL;

import java.util.List;

public class test {
    public static void main(String[] args) {
        NewsDAO newsDAO = new NewsDAO();

        // Fetch and print all news from the database
        List<News> newsList = newsDAO.getAllNews();
        for (News news : newsList) {
            System.out.println(news);
            System.out.println("Total news fetched: "+newsList.size());
        }
    }
}
