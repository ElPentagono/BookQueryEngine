package es.pentagono.serializers;

import com.google.gson.Gson;
import es.pentagono.Metadata;

public class MetadataSerializer implements es.pentagono.MetadataSerializer {
    @Override
    public String serialize(Metadata metadata) {
        Gson gson = new Gson();
        return gson.toJson(metadata);
    }
}
