package es.pentagono.serializers;

import com.google.gson.Gson;
import es.pentagono.ExtendedMetadata;
import es.pentagono.Metadata;
import es.pentagono.MetadataSerializer;

public class JsonMetadataSerializer implements MetadataSerializer {
    @Override
    public String serialize(Metadata metadata) {
        Gson gson = new Gson();
        return gson.toJson(metadata);
    }

    @Override
    public String serialize(ExtendedMetadata extendedMetadata) {
        Gson gson = new Gson();
        return gson.toJson(extendedMetadata);
    }
}
