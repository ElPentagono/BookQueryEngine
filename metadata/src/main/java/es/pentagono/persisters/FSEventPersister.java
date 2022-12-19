package es.pentagono.persisters;

import es.pentagono.Configuration;
import es.pentagono.EventPersister;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FSEventPersister implements EventPersister {
    private static final String EVENTS_HEADER = "ts\tuuid\n";

    @Override
    public void persist(String event) {
        Path path = Path.of(Configuration.getProperty("datalake") + "/events");
        createDirectory(path);
        write(Paths.get(path + "/metadata.log"), event);
    }

    private void createDirectory(Path path) {
        if (!Files.exists(path)) path.toFile().mkdirs();
    }

    private void write(Path path, String event) {
        try {
            if (!Files.exists(path)) createFile(path);
            Files.write(path, event.getBytes(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile(Path path) {
        try {
            Files.write(path, EVENTS_HEADER.getBytes(), CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
