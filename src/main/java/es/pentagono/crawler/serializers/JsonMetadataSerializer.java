package es.pentagono.crawler.serializers;

import com.google.gson.Gson;
import es.pentagono.Metadata;
import es.pentagono.crawler.MetadataSerializer;

public class JsonMetadataSerializer implements MetadataSerializer {

    @Override
    public String serialize(Metadata metadata) {
        Gson gson = new Gson();
        return gson.toJson(metadata);
    }
}
