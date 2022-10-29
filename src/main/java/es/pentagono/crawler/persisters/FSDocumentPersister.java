package es.pentagono.crawler.persisters;

import es.pentagono.crawler.DocumentPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FSDocumentPersister implements DocumentPersister {
    @Override
    public void persist(int id, String metadata, String content) {
        String path = String.format("/BookQueryEngine/documents/%d", id);
        createDirectory(Path.of(path));
        createMetadataFile(path, metadata);
        createContentFile(path, content);
    }

    private void createContentFile(String path, String content) {
        File file = new File(path + "/content.txt");
        createFile(content, file);
    }

    private void createMetadataFile(String path, String metadata) {
        File file = new File(path + "/metadata.json");
        createFile(metadata, file);
    }

    private void createDirectory(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createFile(String string, File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.append(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
