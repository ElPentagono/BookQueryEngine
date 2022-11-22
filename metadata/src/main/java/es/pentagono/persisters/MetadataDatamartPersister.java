package es.pentagono.persisters;

import es.pentagono.MetadataPersister;
import es.pentagono.events.StoreEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MetadataDatamartPersister implements MetadataPersister {

    public void persist(String filename, String content) {
        try {
            createDatamartDirectory();
            if (existsInDatamart(filename)) return;
            writeDatamartContent(filename, content);
            writeDatamartEvent(new StoreEvent(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean existsInDatamart(String filename) throws IOException {
        Path path = Paths.get(System.getenv("DATAMART") + "/metadata/events/updates.log");
        return Files.readAllLines(path).stream()
                .map(line -> line.split(" ")[0])
                .anyMatch(s -> s.contains(filename));
    }

    private void writeDatamartEvent(StoreEvent event) throws IOException {
        if (!Files.exists(Paths.get(System.getenv("DATAMART") + "/metadata/events/updates.log")))
            addHeaderToStoreEvent();
        FileWriter writer = new FileWriter(System.getenv("DATAMART") + "/metadata/events/updates.log", true);
        writer.write("\n" + event.filename + "\t" + event.ts.getNanos());
        writer.close();
    }

    private void addHeaderToStoreEvent() throws IOException {
        FileWriter writer = new FileWriter(System.getenv("DATAMART") + "/metadata/events/updates.log");
        writer.write("filename\tts");
        writer.close();
    }

    private void writeDatamartContent(String filename, String content) throws IOException {
        FileWriter writer = new FileWriter(System.getenv("DATAMART") + "/metadata/content/" + filename);
        writer.write(content);
        writer.close();
    }


    private void createDatamartDirectory() throws IOException {
        if (!Files.exists(Paths.get(System.getenv("DATAMART") + "/metadata"))) {
            new File(System.getenv("DATAMART") + "/metadata/content").mkdirs();
            new File(System.getenv("DATAMART") + "/metadata/events").mkdirs();
        }
    }

}
