package es.pentagono.serializers;

import com.google.gson.*;
import es.pentagono.Metadata;
import es.pentagono.MetadataSerializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;

public class JsonMetadataSerializer implements MetadataSerializer {

    @Override
    public String serialize(Metadata metadata) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString())).create();
        return gson.toJson(metadata);
    }
}
