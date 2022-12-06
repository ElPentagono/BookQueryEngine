package es.pentagono.serializers;

import com.google.gson.Gson;
import es.pentagono.Metadata;
import es.pentagono.MetadataSerializer;

public class GsonMetadataSerializer implements MetadataSerializer {
    @Override
    public String serialize(Metadata metadata) {
        Gson gson = new Gson();
        return gson.toJson(metadata);
    }
}
