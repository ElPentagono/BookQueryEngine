package es.pentagono.crawler.persisters;

import es.pentagono.crawler.DocumentPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemDocumentPersister implements DocumentPersister {
    private static final String TSV_HEADER = "ts\tsrc\tuuid\tmd5\n";
    private static final String DATALAKE = "C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/datalake";
    private static final String DOCUMENTS_LAKE = "C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/documents";
    @Override
    public void persist(String id, String metadata, String content) {
        String path = String.format(DOCUMENTS_LAKE + "/%s", id);  // TODO
        createDirectory(path);
        createFile(path + "/metadata.json", metadata);
        createFile(path + "/content.txt", content);
    }

    @Override
    public void persist(String event) {
        String path = String.format(DATALAKE);  // TODO
        if (! Files.exists(Paths.get(path))) createLogFile(path);
        writeEvent(path + "/updates.log", event);
    }

    private void createDirectory(String path) {
        if (!Files.exists(Paths.get(path))) new File(path).mkdirs();
    }

    private void createLogFile(String path) {
        new File(path).mkdirs();
        createFile(path + "/updates.log", TSV_HEADER);
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
