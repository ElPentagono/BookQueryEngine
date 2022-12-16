package es.pentagono.deserializer;

import com.google.gson.*;
import es.pentagono.Metadata;
import es.pentagono.MetadataDeserializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonMetadataDeserializer implements MetadataDeserializer {

    public static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

        private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), FORMATTER);
        }
    }



    @Override
    public Metadata deserialize(String metadata) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
        return gson.fromJson(metadata, Metadata.class);
    }
}
