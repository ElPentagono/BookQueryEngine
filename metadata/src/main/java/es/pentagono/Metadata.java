package es.pentagono;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Metadata {
    private final String title;
    private final String author;
    private final String language;
    private final LocalDate releaseDate;


    public Metadata(String title, String author, String language, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.releaseDate = releaseDate;
    }

    public String title() {
        return this.title;
    }

    public String author() {
        return this.author;
    }

    public String language() {
        return this.language;
    }

    public LocalDate releaseDate() {
        return this.releaseDate;
    }
}
