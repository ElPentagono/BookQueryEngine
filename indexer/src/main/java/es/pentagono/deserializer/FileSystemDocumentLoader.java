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
            String path = String.format(System.getenv("DATALAKE") + "/documents/%s", id); // TODO
            return new Document(id, getMetadata(path + "/metadata.json"), getContentOf(path + "/content.txt"));
        }
        catch (Exception exception) {
            throw exception;
        }

    }

    private Metadata getMetadata(String path) {
        return builder.build(getContentOf(path));
    }

    private String getContentOf(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
