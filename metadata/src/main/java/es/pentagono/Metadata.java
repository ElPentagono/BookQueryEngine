package es.pentagono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Metadata {
    public String title;
    public String author;
    public String language;
    public String releaseDate;
    private int numberOfWords;


    public Metadata(String title, String author, String language, String releaseDate, String documentId) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.releaseDate = releaseDate;
        this.numberOfWords = getNumberOfWords(documentId);
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

    public String releaseDate() {
        return this.releaseDate;
    }

    public int numberOfWords() {
        return this.numberOfWords;
    }

    public int getNumberOfWords(String documentId) {
        try {
            Pattern pattern = Pattern.compile("\\b(\\w+|\\d+)\\b"); //\\b\\w+\\b|\\s+
            return (int) Files.lines(documentPath(documentId))
                    .flatMap(line -> Stream.of(line.split(" ")))
                    .filter(word ->  pattern.matcher(word).matches())
                    .count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path documentPath(String documentId) {
        return Path.of("/app/datalake" + "/documents/" + documentId + "/content.txt");
    }
}
