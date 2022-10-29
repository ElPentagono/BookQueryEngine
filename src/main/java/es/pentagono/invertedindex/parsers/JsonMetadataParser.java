package es.pentagono.invertedindex.parsers;
import com.google.gson.Gson;
import es.pentagono.Metadata;
import es.pentagono.invertedindex.MetadataParser;

public class JsonMetadataParser implements MetadataParser {

    public Metadata jsonParse(String jsonString){
        Gson g = new Gson();
        return g.fromJson(jsonString, Metadata.class);

    }
}
