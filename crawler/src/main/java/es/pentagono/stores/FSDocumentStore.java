package es.pentagono.stores;

import es.pentagono.*;
import es.pentagono.persisters.FSDocumentPersister;

import java.util.UUID;

public class FSDocumentStore implements DocumentStore {
    public final DocumentPersister Persister = new FSDocumentPersister();
    public final MetadataSerializer metadataSerializer;
    public final EventSerializer eventSerializer;

    public FSDocumentStore(MetadataSerializer metadataSerializer, EventSerializer eventSerializer) {
        this.metadataSerializer = metadataSerializer;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public String store(Document document) {
        String uuid = UUID.randomUUID().toString();
        Persister.persist(uuid, metadataSerializer.serialize(document.metadata), document.content);
        return uuid;
    }

    @Override
    public void store(Event event) {
        Persister.persist(eventSerializer.serialize(event));
    }
}
