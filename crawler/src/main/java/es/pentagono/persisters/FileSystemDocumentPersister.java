package es.pentagono.persisters;

import es.pentagono.DocumentPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemDocumentPersister implements DocumentPersister {
    private final String TSV_HEADER = "ts\tsrc\tuuid\tmd5\n";
    @Override
    public void persist(String id, String metadata, String content) {
        String path = String.format(System.getenv("DATALAKE") + "/documents" + "/%s", id);
        createDirectory(path);
        createDirectory(System.getenv("DATALAKE") + "/events");
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
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeEvent(String path, String event) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(event);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
