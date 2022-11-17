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
            FileWriter writer = new FileWriter("C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/datamarts/metadata/" + filename);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void createDatamartDirectory() {
        if (!Files.exists(Paths.get("C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/datamarts/metadata/")))
            new File("C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/datamarts/metadata/").mkdirs();
    }

}
