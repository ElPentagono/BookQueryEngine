package es.pentagono.serializers;

import es.pentagono.Metadata;
import es.pentagono.MetadataSerializer;

public class SQLMetadataSerializer implements MetadataSerializer {
    @Override
    public String serialize(Metadata metadata) {
        return "'" + metadata.title().replaceAll("'","") + "','"
                + metadata.author().replaceAll("'","") + "','"
                + metadata.language().replaceAll("'","") + "','"
                + metadata.releaseDate() + "'";
    }
}
