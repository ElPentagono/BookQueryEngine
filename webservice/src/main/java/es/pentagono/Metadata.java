package es.pentagono;

public class Metadata {

    public String title;
    public String author;
    public String language;
    public String releaseDate;

    public Metadata(String title, String author, String language, String releaseDate) {
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

    public String ReleaseDate() {
        return this.releaseDate;
    }
}
