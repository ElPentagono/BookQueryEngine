package es.pentagono.stores;

import es.pentagono.*;
import es.pentagono.writers.FSDocumentWriter;

import java.util.UUID;

public class FSDocumentStore implements DocumentStore {
    public final DocumentWriter writer = new FSDocumentWriter();
    public final MetadataSerializer metadataSerializer;

    public FSDocumentStore(MetadataSerializer metadataSerializer) {
        this.metadataSerializer = metadataSerializer;
    }

    @Override
    public String store(Document document) {
        String uuid = UUID.randomUUID().toString();
        writer.write(uuid, metadataSerializer.serialize(document.metadata), document.content);
        return uuid;
    }
}
