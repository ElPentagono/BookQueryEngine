package es.pentagono.serializers;

import es.pentagono.Appearance;
import es.pentagono.AppearanceSerializer;
import es.pentagono.MetadataDeserializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonAppearanceSerializer implements AppearanceSerializer {

    private final MetadataDeserializer deserializer;

    public JsonAppearanceSerializer(MetadataDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public String serialize(Appearance appearance) {
        return "{\"book\":\"" + bookTitle(appearance.uuid) + "\",\"position\":" + appearance.position + "}";
    }

    private String bookTitle(String uuid) {
        try {
            return deserializer.deserialize(Files.readString(Path.of(System.getenv("DATAMART") + "/metadata/content/" + uuid))).title;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
