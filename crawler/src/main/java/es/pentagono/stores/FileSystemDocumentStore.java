package es.pentagono.stores;

import es.pentagono.*;
import es.pentagono.persisters.FileSystemDocumentPersister;

import java.util.UUID;

public class FileSystemDocumentStore implements DocumentStore {
    public final DocumentPersister Persister = new FileSystemDocumentPersister();
    public final MetadataSerializer metadataSerializer;
    public final EventSerializer eventSerializer;

    public FileSystemDocumentStore(MetadataSerializer metadataSerializer, EventSerializer eventSerializer) {
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
