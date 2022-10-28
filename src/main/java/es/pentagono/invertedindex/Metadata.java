package es.pentagono.invertedindex;

public class Metadata {
    public final String title;
    public final String author;
    public final String releaseDate;
    public final String updatedDate;
    public final String language;

    public Metadata(String title, String author, String releaseDate, String updatedDate, String language) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.updatedDate = updatedDate;
        this.language = language;
    }
}
