package es.pentagono.crawler.persisters;

import es.pentagono.crawler.DocumentPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemDocumentPersister implements DocumentPersister {
    private static final String TSVHEADER = "ts\tsrc\tuuid\tmd5\n";
    @Override
    public void persist(String id, String metadata, String content) {
        String path = String.format("C:/Users/juanc/IdeaProjects/BookQueryEngine/documents/%s", id);  // TODO
        createDirectory(path);
        createMetadataFile(path, metadata);
        createContentFile(path, content);
    }

    @Override
    public void persist(String event) {
        String path = String.format("C:/Users/juanc/IdeaProjects/BookQueryEngine/datalake/updates.log");  // TODO
        createUpdatesFile(path);
        writeEvent(path, event);
    }

    private void createContentFile(String path, String content) {
        createFile(path + "/content.txt", content);
    }

    private void createMetadataFile(String path, String metadata) {
        createFile(path + "/metadata.json", metadata);
    }

    private void createDirectory(String path) {
        if (!Files.exists(Paths.get(path))) new File(path).mkdirs();
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

    private static void createUpdatesFile(String path) {
        if (!Files.exists(Paths.get(path))) createFile(path, TSVHEADER);
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
