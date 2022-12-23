package es.pentagono;

import java.time.LocalDate;

public class Metadata {

    public final String title;
    public final String author;
    public final String language;
    public final LocalDate releaseDate;

    public Metadata(String title, String author, String language, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.releaseDate = releaseDate;
    }
}
