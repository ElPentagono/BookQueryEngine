package es.pentagono.crawler.stores;

import es.pentagono.Document;
import es.pentagono.crawler.DocumentStore;
import es.pentagono.crawler.persisters.FSDocumentPersister;
import es.pentagono.crawler.serializers.JsonMetadataSerializer;

import java.util.UUID;

public class FSDocumentStore implements DocumentStore {
    public JsonMetadataSerializer metadataSerializer;
    public FSDocumentPersister persister;

    public FSDocumentStore(JsonMetadataSerializer serializer, FSDocumentPersister persister) {
        this.metadataSerializer = serializer;
        this.persister = persister;
    }

    @Override
    public String store(Document document) {
        String uuid = UUID.randomUUID().toString();
        persister.persist(document.id + "_" + uuid, metadataSerializer.serialize(document.metadata), document.content);
        return uuid;
    }
}
