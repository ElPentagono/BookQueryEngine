package es.pentagono.serializers;

import es.pentagono.Metadata;
import es.pentagono.MetadataSerializer;

import java.util.Objects;

public class SQLMetadataSerializer implements MetadataSerializer {
    @Override
    public String serialize(Metadata metadata) {
        findNullValues(
                metadata.title().replaceAll("'",""),
                metadata.author().replaceAll("'",""),
                metadata.language().replaceAll("'",""),
                metadata
                );
        return "'" + metadata.title() + "','"
                + metadata.author() + "','"
                + metadata.language() + "','"
                + metadata.releaseDate() + "'";
    }

    private void findNullValues(String title, String author, String language, Metadata metadata) {

    }
}
