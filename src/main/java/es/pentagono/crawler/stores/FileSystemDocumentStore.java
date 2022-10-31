package es.pentagono.crawler.stores;

import es.pentagono.Document;
import es.pentagono.crawler.*;
import es.pentagono.crawler.persisters.FileSystemDocumentPersister;
import es.pentagono.crawler.serializers.TsvEventSerializer;

import java.util.UUID;

public class FileSystemDocumentStore implements DocumentStore {
    public final MetadataSerializer metadataSerializer;
    public final DocumentPersister Persister = new FileSystemDocumentPersister();
    public final EventSerializer Serializer = new TsvEventSerializer();

    public FileSystemDocumentStore(MetadataSerializer serializer) {
        this.metadataSerializer = serializer;
    }

    @Override
    public String store(Document document) {
        String uuid = UUID.randomUUID().toString();
        Persister.persist(uuid, metadataSerializer.serialize(document.metadata), document.content);
        return uuid;
    }

    @Override
    public void store(Event event) {
        Persister.persist(Serializer.serialize(event));
    }


}
