package bg.berov.sportnews.model;

import android.graphics.drawable.Drawable;

public class NewsItem {

    private String label;
    private String author;
    private String date;
    private String url;
    private String section;
    private Drawable thumbnail;

    public NewsItem(String label, String author, String date, String url, String section, Drawable thumbnail) {
        this.label = label;
        this.author = author;
        this.date = date;
        this.url = url;
        this.section = section;
        this.thumbnail = thumbnail;
    }

    public String getLabel() {
        return label;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getSection() {
        return section;
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }
}
