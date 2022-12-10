package es.pentagono.persisters;

import es.pentagono.InvertedIndexPersister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FSInvertedIndexPersister implements InvertedIndexPersister {

    private static final String INDEX_HEADER = "filename";
    private static final String CONFIG_HEADER = "uuid\n";
    private static final String EVENTS_HEADER = "ts\tuuid\tmodified\tcomment\n";

    @Override
    public void persistConfig(Map<String, String> invertedIndex) {
        for (String word : invertedIndex.keySet()) {
            if (word.length() <= 3) continue;
            Path path = Path.of("/appI/invertedIndexDatamart/index/" + word.charAt(0) + "/" + word.substring(0, 2)); // System.getenv("DATAMART") + "/invertedIndex/index/" + word.charAt(0) + "/" + word.substring(0, 2)
            createDirectory(path);
            write(Paths.get(path + String.format("/%s", word)), INDEX_HEADER, invertedIndex.get(word));
        }
    }

    @Override
    public void persistConfig(String event) {
        Path path = Path.of("/appI/invertedIndexDatamart"); // System.getenv("DATAMART") + "/invertedIndex/events"
        createDirectory(path);
        write(Paths.get(path + "/indexer.config"), CONFIG_HEADER, event);
    }

    @Override
    public void persistDatalake(String event) {
        Path path = Path.of("/app/datalake/events");
        createDirectory(path);
        write(Paths.get(path + "/updates.log"), EVENTS_HEADER, event);
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
