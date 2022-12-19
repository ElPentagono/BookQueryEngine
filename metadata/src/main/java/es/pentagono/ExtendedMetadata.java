package es.pentagono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ExtendedMetadata {

    public final String uuid;
    public final Metadata metadata;
    public final int wordsCount;

    public ExtendedMetadata(String uuid, Metadata metadata) {
        this.uuid = uuid;
        this.metadata = metadata;
        this.wordsCount = countWords(uuid);
    }

    private int countWords(String uuid) {
        try {
            Pattern pattern = Pattern.compile("\\b(\\w+|\\d+)\\b");
            return (int) Files.lines(getPath(uuid))
                    .flatMap(line -> Stream.of(line.split(" ")))
                    .filter(word ->  pattern.matcher(word).matches())
                    .count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath(String uuid) {
        return Path.of("/app/datalake" + "/documents/" + uuid + "/content.txt");
    }
}
