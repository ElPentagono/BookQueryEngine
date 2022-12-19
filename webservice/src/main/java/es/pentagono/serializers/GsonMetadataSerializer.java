package es.pentagono.serializers;

import com.google.gson.Gson;
import es.pentagono.Metadata;
import es.pentagono.MetadataSerializer;

public class GsonMetadataSerializer implements MetadataSerializer {
    @Override
    public String serialize(Metadata metadata) {
        return new Gson().toJson(metadata);
    }
}
