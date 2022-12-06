package es.pentagono.loaders;

import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;
import es.pentagono.MetadataLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FSMetadataLoader implements MetadataLoader {

    private final MetadataDeserializer deserializer;

    public FSMetadataLoader(MetadataDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Metadata load(String uuid) {
        try {
            return deserializer.deserialize(Files.readString(Path.of(System.getenv("DATAMART") + "/metadata/content/" + uuid)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
