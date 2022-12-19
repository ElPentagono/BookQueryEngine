package es.pentagono.readers;

import es.pentagono.Configuration;
import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;
import es.pentagono.MetadataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FSMetadataReader implements MetadataReader {

    MetadataDeserializer deserializer;

    public FSMetadataReader(MetadataDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Metadata read(String uuid) {
        try {
            return deserializer.deserialize(Files.readString(getPath(uuid)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath(String uuid) {
        return Path.of(Configuration.getProperty("datalake") + "/documents/" + uuid + "/metadata.json");
    }
}
