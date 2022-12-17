package es.pentagono.writers;

import es.pentagono.Configuration;
import es.pentagono.DocumentWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;

public class FSDocumentWriter implements DocumentWriter {

    @Override
    public void write(String id, String metadata, String content) {
        Path path = Path.of(String.format(Configuration.getProperty("datalake") + "/documents/%s", id));
        createDirectory(path);
        write(Paths.get(path + "/content.txt"), content);
        write(Paths.get(path + "/metadata.json"), metadata);
    }

    private void createDirectory(Path path) {
        if (!Files.exists(path)) path.toFile().mkdirs();
    }

    private void write(Path path, String content) {
        try {
            if (!Files.exists(path)) Files.createFile(path);
            Files.write(path, content.getBytes(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
