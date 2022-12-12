package es.pentagono.persisters;

import es.pentagono.DocumentPersister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FSDocumentPersister implements DocumentPersister {

    private static final String CONFIG_HEADER = "src\tuuid\tmd5\n";
    private static final String UPDATE_HEADER = "ts\tuuid\tcontent\tcomment\n";

    @Override
    public void persist(String id, String metadata, String content) {
        Path path = Path.of(String.format("/app/datalake/documents/%s", id)); // String.format(System.getenv("DATALAKE") + "/documents" + "/%s", id)
        createDirectory(path);
        write(Paths.get(path + "/content.txt"), "", content);
        write(Paths.get(path + "/metadata.json"), "", metadata);
    }

    @Override
    public void persistConfig(String event) {
        Path path = Path.of("/app/datalake"); // System.getenv("DATALAKE") + "/events"
        createDirectory(path);
        write(Paths.get(path + "/crawler.config"), CONFIG_HEADER, event);
    }

    @Override
    public void persistEvent(String event) {
        Path path = Path.of("/app/datalake/events"); // System.getenv("DATALAKE") + "/events"
        createDirectory(path);
        write(Paths.get(path + "/updates.log"), UPDATE_HEADER, event);
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
