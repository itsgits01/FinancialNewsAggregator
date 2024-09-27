package org.example.Database;

public class NewsArticle {
    private String title;
    private String url;
    private String summary;
    private String timePublished;
    private String source;

    public NewsArticle(String title, String url, String summary, String timePublished, String source) {
        this.title = title;
        this.url = url;
        this.summary = summary;
        this.timePublished = timePublished;
        this.source = source;
    }

    // Getters for each field
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
}
