package es.pentagono;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Map;

public class MetadataBuilder {

    public Metadata build(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Metadata.class);

    }
    public Metadata build(Map<String, String> metadata) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(metadata);
        return gson.fromJson(jsonElement, Metadata.class);
    }
}
