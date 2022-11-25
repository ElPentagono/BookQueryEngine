package es.pentagono.persisters;

import es.pentagono.DocumentPersister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileSystemDocumentPersister implements DocumentPersister {
    private static final String TSV_HEADER = "ts\tsrc\tuuid\tmd5\n";
    @Override
    public void persist(String id, String metadata, String content) {
        String path = String.format(System.getenv("DATALAKE") + "/documents" + "/%s", id);
        createDirectory(System.getenv("DATALAKE") + "/events");
        createDirectory(path);
        createFile(path + "/metadata.json", metadata);
        createFile(path + "/content.txt", content);
    }

    @Override
    public void persist(String event) {
        String path = String.format(System.getenv("DATALAKE"));
        if (! Files.exists(Paths.get(path + "/events/updates.log"))) createLogFile(path);
        writeEvent(path + "/events/updates.log", event);
    }

    private void createDirectory(String path) {
        if (!Files.exists(Paths.get(path))) new File(path).mkdirs();
    }

    private void createLogFile(String path) {
        new File(path).mkdirs();
        createFile(path + "/events/updates.log", TSV_HEADER);
    }

    private static void createFile(String file, String text) {
        try {
            Files.write(Path.of(file), text.getBytes(), CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeEvent(String path, String event) {
        try {
            Files.write(Path.of(path), event.getBytes(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
