package es.pentagono.persisters;

import es.pentagono.InvertedIndexPersister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileSystemInvertedIndexPersister implements InvertedIndexPersister {

    private static final String INDEX_HEADER = "id\tposition\n";
    private static final String EVENTS_HEADER = "ts\tuuid\n";

    @Override
    public void persist(Map<String, String> invertedIndex) {
        for (String word : invertedIndex.keySet()) {
            Path path = Path.of(System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0, 2));
            createDirectory(path);
            write(Paths.get(path + String.format("/%s", word)), INDEX_HEADER, invertedIndex.get(word));
        }
    }

    @Override
    public void persist(String event) {
        Path path = Path.of(System.getenv("DATAMART") + "/invertedIndex/events");
        createDirectory(path);
        write(Paths.get(path + "/indexed.log"), EVENTS_HEADER, event);
    }

    private void createDirectory(Path path) {
        if (!Files.exists(path)) path.toFile().mkdirs();
    }

    private void write(Path path, String header, String content) {
        try {
            if (!Files.exists(path)) createFile(path, header);
            Files.write(path, content.getBytes(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile(Path path, String text) {
        try {
            Files.write(path, text.getBytes(), CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
