package es.pentagono.deserializer;

import com.google.gson.Gson;
import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;

public class GsonMetadataDeserializer implements MetadataDeserializer {
    @Override
    public Metadata deserialize(String metadata) {
        Gson gson = new Gson();
        return gson.fromJson(metadata, Metadata.class);
    }
}
