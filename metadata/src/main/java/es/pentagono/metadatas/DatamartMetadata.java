package es.pentagono.metadatas;

import es.pentagono.Metadata;

public class DatamartMetadata implements Metadata {
    public String title;
    public String author;
    public String language;
    public String releaseDate;

    public DatamartMetadata(String title, String author, String language, String releaseDate) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.releaseDate = releaseDate;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String author() {
        return this.author;
    }

    @Override
    public String language() {
        return this.language;
    }

    public String ReleaseDate() {
        return this.releaseDate;
    }

}
