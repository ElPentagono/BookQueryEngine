package es.pentagono.persisters;

import es.pentagono.DocumentPersister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileSystemDocumentPersister implements DocumentPersister {

    private static final String EVENTS_HEADER = "ts\tsrc\tuuid\tmd5";

    @Override
    public void persist(String id, String metadata, String content) {
        Path path = Path.of(String.format(System.getenv("DATALAKE") + "/documents" + "/%s", id));
        createDirectory(path);
        write(Paths.get(path + "/content.txt"), "", content);
        write(Paths.get(path + "/metadata.json"), "", metadata);
    }

    @Override
    public void persist(String event) {
        Path path = Path.of(System.getenv("DATALAKE") + "/events");
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

    private static void createFile(Path path, String text) {
        try {
            Files.write(path, text.getBytes(), CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
