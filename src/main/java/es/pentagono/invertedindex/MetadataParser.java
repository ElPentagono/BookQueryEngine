package es.pentagono.invertedindex;

import es.pentagono.Metadata;

public interface MetadataParser {

    Metadata jsonParse(String jsonString);
}
