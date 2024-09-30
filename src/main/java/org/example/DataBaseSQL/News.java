package org.example.DataBaseSQL;


public class News {
    private String title;
    private String url;
    private String summary;
    private String timePublished;
    private String source;

    // Constructor
    public News(String title, String url, String summary, String timePublished, String source) {
        this.title = title;
        this.url = url;
        this.summary = summary;
        this.timePublished = timePublished;
        this.source = source;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSummary() {
        return summary;
    }

    public String getTimePublished() {
        return timePublished;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "URL: " + url + "\n" +
                "Summary: " + summary + "\n" +
                "Time Published: " + timePublished + "\n" +
                "Source: " + source + "\n" +
                "-----------------------------";
    }
}

