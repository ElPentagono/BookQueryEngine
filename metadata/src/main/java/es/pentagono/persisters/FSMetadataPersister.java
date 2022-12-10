package es.pentagono.persisters;

import es.pentagono.Document;
import es.pentagono.Event;
import es.pentagono.MetadataPersister;
import es.pentagono.events.StoreEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FSMetadataPersister implements MetadataPersister {

    public static final String DATAMART = "/app"; // System.getenv("DATAMART");
    public static final String CONFIG_HEADER = "filename";

    public void persist(Document document) {
        try {
            createDatamartDirectory();
            if (existsInDatamart(document.uuid)) return;
            write(Paths.get(DATAMART + "/metadata/content/" + document.uuid), document.metadata, CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void persist(Event event) {
        try {
            write(Paths.get(DATAMART + "/metadata/events/updates.log"), new StoreEvent(((StoreEvent) event).filename).toString(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean existsInDatamart(String filename) throws IOException {
        return Files.readAllLines(Paths.get(DATAMART + "/metadata/events/updates.log")).stream()
                .map(line -> line.split(" ")[0])
                .anyMatch(s -> s.contains(filename));
    }

    private void createDatamartDirectory() throws IOException {
        if (notExist(DATAMART + "/metadata")) {
            createDirectory(DATAMART + "/metadata/content");
            addHeaderStoreEventFile();
        }
    }

    private static void write(Path path, String text, StandardOpenOption option) throws IOException {
        Files.write(path, text.getBytes(), option);
    }

    private static boolean notExist(String file) {
        return !Files.exists(Paths.get(file));
    }

    private void addHeaderStoreEventFile() throws IOException {
        write(Paths.get(DATAMART + "/metadata.config"), CONFIG_HEADER, CREATE);
    }

    private void createDirectory(String path) {
        new File(path).mkdirs();
    }



}