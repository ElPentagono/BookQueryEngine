package es.pentagono.deserializer;

import es.pentagono.Document;
import es.pentagono.DocumentLoader;
import es.pentagono.Metadata;
import es.pentagono.MetadataBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemDocumentLoader implements DocumentLoader {

    public final MetadataBuilder builder = new MetadataBuilder();

    @Override
    public Document load(String id) {
        try {
            String path = String.format(System.getenv("DATALAKE") + "/documents/%s", id);
            return new Document(id, metadata(path + "/metadata.json"), content(path + "/content.txt"));
        }
        catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    private Metadata metadata(String path) throws IOException {
        return builder.build(content(path));
    }

    private String content(String path) throws IOException {
            return Files.readString(Paths.get(path));
    }

}
