package bg.berov.jukebox.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Song implements Serializable, Comparable, Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.artist);
        dest.writeInt(this.year);
        dest.writeString(this.genre);
        dest.writeInt(this.imageResourceID);
        dest.writeString(this.link);
    }

    protected Song(Parcel in) {
        this.name = in.readString();
        this.artist = in.readString();
        this.year = in.readInt();
        this.genre = in.readString();
        this.imageResourceID = in.readInt();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}

