package es.pentagono.writers;

import es.pentagono.Configuration;
import es.pentagono.InvertedIndexWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FSInvertedIndexWriter implements InvertedIndexWriter {

    private static final String INDEX_HEADER = "uuid\tposition";

    @Override
    public void write(Map<String, String> invertedIndex) {
        for (String word : invertedIndex.keySet()) {
            if (word.length() <= 3) continue;
            Path path = Path.of(Configuration.getProperty("datamart") + "/index/" + word.charAt(0) + "/" + word.substring(0, 2));
            createDirectory(path);
            write(Paths.get(path + String.format("/%s", word)), invertedIndex.get(word));
        }
    }

    private void createDirectory(Path path) {
        if (!Files.exists(path)) path.toFile().mkdirs();
    }

    private void write(Path path, String content) {
        try {
            if (!Files.exists(path)) createFile(path);
            Files.write(path, content.getBytes(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile(Path path) {
        try {
            Files.write(path, INDEX_HEADER.getBytes(), CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
