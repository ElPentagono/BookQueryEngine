package es.pentagono.crawler.persisters;

import es.pentagono.crawler.DocumentPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FSDocumentPersister implements DocumentPersister {
    @Override
    public void persist(String id, String metadata, String content) {
        String path = String.format("C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/documents/%s", id);  // TODO
        createDirectory(path);
        createFile(path + "/metadata.json", metadata);
        createFile(path + "/content.txt", content);
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
}
