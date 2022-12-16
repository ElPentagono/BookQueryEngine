package es.pentagono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ExtendedMetadata {

    public final Metadata metadata;
    private final int numberOfWords;

    public ExtendedMetadata(Metadata metadata, String documentId) {
        this.metadata = metadata;
        this.numberOfWords = calculateNumberOfWords(documentId);
    }

    public int numberOfWords() {
        return numberOfWords;
    }

    private int calculateNumberOfWords(String documentId) {
        try {
            Pattern pattern = Pattern.compile("\\b(\\w+|\\d+)\\b");
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
