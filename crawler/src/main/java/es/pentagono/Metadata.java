package es.pentagono;

import java.time.LocalDate;

public class Metadata {
    public String title;
    public String author;
    public String language;
    public LocalDate releaseDate;

    public Metadata(String title, String author, String language, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.releaseDate = releaseDate;
    }
}
