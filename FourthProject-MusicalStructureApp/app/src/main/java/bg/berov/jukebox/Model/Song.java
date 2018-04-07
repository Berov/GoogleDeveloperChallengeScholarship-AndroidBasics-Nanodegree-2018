package bg.berov.jukebox.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Song implements Serializable, Comparable {
    private static int ID = 0;
    private String name;
    private String artist;
    private int year;
    private String genre;
    private int imageResourceID;
    private String link;

    public Song(String name, String artist, int year, String genre, int imageResourceID, String link) {
        if (!name.isEmpty()) {
            this.name = name;
        } else {
            this.name = "No name song " + ID++;
        }

        if (!artist.isEmpty()) {
            this.artist = artist;
        } else {
            this.artist = "Unknown Artist ";
        }

        this.year = year;

        if (!genre.isEmpty()) {
            this.genre = genre;
        } else {
            this.genre = "Unknown genre";
        }

        this.imageResourceID = imageResourceID;

        if (!link.isEmpty()) {
            this.link = link;
        } else {
            this.link = "No link";
        }
    }


    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public String getYearAsString() {
        return Integer.toString(year);
    }

    public String getGenre() {
        return genre;
    }

    public int getImage() {
        return imageResourceID;
    }

    public String getLink() {
        return link;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return this.year - ((Song) o).getYear();
    }
}

