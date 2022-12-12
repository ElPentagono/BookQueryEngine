package es.pentagono.deserializer;

import es.pentagono.Document;
import es.pentagono.DocumentLoader;
import es.pentagono.Metadata;
import es.pentagono.MetadataBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FSDocumentLoader implements DocumentLoader {

    public final MetadataBuilder builder = new MetadataBuilder();

    @Override
    public Document load(String id) {
        try {
            String path = String.format("/app/datalake/documents/%s", id); // String.format(System.getenv("DATALAKE") + "/documents/%s", id)
            return new Document(id, metadata(path + "/metadata.json"), content(path + "/content.txt"));
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Metadata metadata(String path) throws IOException {
        return builder.build(content(path));
    }

    private String content(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

}
