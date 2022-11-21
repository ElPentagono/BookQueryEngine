package es.pentagono.persisters;

import es.pentagono.MetadataPersister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MetadataDatamartPersister implements MetadataPersister {

    public void persist(String filename, String content) {
        try {
            createDatamartDirectory();
            FileWriter writer = new FileWriter(System.getenv("DATAMART") + "/metadata/" + filename);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void createDatamartDirectory() {
        if (!Files.exists(Paths.get(System.getenv("DATAMART") + "/metadata")))
            new File(System.getenv("DATAMART") + "/metadata").mkdirs();
    }

}
