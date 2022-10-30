package es.pentagono.crawler.stores;

import es.pentagono.Document;
import es.pentagono.crawler.DocumentStore;
import es.pentagono.crawler.MetadataSerializer;
import es.pentagono.crawler.persisters.FSDocumentPersister;
import es.pentagono.crawler.serializers.JsonMetadataSerializer;

import java.util.UUID;

public class FSDocumentStore implements DocumentStore {
    public final MetadataSerializer metadataSerializer;
    public final FSDocumentPersister persister = new FSDocumentPersister();

    public FSDocumentStore(MetadataSerializer serializer) {
        this.metadataSerializer = serializer;
    }

    @Override
    public String store(Document document) {
        String uuid = UUID.randomUUID().toString();
        persister.persist(document.id + "_" + uuid, metadataSerializer.serialize(document.metadata), document.content);
        return uuid;
    }
}
