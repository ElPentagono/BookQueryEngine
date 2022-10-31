package es.pentagono.invertedindex.builders;
import com.google.gson.Gson;
import es.pentagono.Metadata;

public class MetadataBuilder {

    public Metadata build(String json) {
        Gson g = new Gson();
        return g.fromJson(json, Metadata.class);

    }
}
