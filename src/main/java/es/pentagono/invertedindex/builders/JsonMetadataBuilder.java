package es.pentagono.invertedindex.builders;
import com.google.gson.Gson;
import es.pentagono.Metadata;

public class JsonMetadataBuilder  {

    public Metadata metadataBuilder(String jsonString){
        Gson g = new Gson();
        return g.fromJson(jsonString, Metadata.class);

    }
}
