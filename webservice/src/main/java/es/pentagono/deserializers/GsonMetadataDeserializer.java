package es.pentagono.deserializers;

import com.google.gson.Gson;
import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;

public class GsonMetadataDeserializer implements MetadataDeserializer {
    @Override
    public Metadata deserialize(String metadata) {
        return new Gson().fromJson(metadata, Metadata.class);
    }
}