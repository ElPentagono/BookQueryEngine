package es.pentagono.crawler.stores;

import es.pentagono.Document;
import es.pentagono.crawler.DocumentStore;
import es.pentagono.crawler.persisters.FSDocumentPersister;
import es.pentagono.crawler.serializers.JsonMetadataSerializer;

public class FSDocumentStore implements DocumentStore {
    public JsonMetadataSerializer metadataSerializer;
    public FSDocumentPersister persister;

    public FSDocumentStore(JsonMetadataSerializer serializer, FSDocumentPersister persister) {
        this.metadataSerializer = serializer;
        this.persister = persister;
    }

    @Override
    public void store(int id, Document document) {
        persister.persist(id, metadataSerializer.serialize(document.metadata), document.content);
    }
}
