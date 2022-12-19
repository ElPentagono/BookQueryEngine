package es.pentagono.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;

import java.time.LocalDate;

public class GsonMetadataDeserializer implements MetadataDeserializer {
    @Override
    public Metadata deserialize(String metadata) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, typeOft, context) -> LocalDate.parse(json.getAsString()))
                .create();
        return gson.fromJson(metadata, Metadata.class);
    }
}
