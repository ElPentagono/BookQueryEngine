package es.pentagono.readers;

import es.pentagono.Document;
import es.pentagono.DocumentReader;
import es.pentagono.MetadataDeserializer;

public class FSDocumentReader implements DocumentReader {
    MetadataDeserializer deserializer;

    public FSDocumentReader(MetadataDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Document read() {
        return null;
    }
}
