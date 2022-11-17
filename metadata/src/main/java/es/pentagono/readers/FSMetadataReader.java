package es.pentagono.readers;

import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;
import es.pentagono.MetadataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FSMetadataReader implements MetadataReader {
    MetadataDeserializer deserializer;

    public FSMetadataReader(MetadataDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Metadata read(String documentId) {
        try {
            return deserializer.deserialize(new String(Files.readAllBytes(Paths.get(documentPath(documentId)))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String documentPath(String documentId) {
        return "C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/datalake/documents/" + documentId + "/metadata.json";
    }
}
