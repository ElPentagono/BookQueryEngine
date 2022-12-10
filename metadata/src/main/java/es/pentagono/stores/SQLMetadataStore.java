package es.pentagono.stores;

import es.pentagono.*;
import es.pentagono.events.StoreEvent;
import es.pentagono.persisters.SQLMetadataPersister;
import es.pentagono.serializers.SQLMetadataSerializer;

public class SQLMetadataStore implements Store {
    public MetadataSerializer serializer;
    public MetadataPersister persister;

    public SQLMetadataStore() {
        this.serializer = new SQLMetadataSerializer();
        this.persister = new SQLMetadataPersister();
    }

    @Override
    public void store(Document document) {
        persister.persist(document);
        persister.persist(new StoreEvent(document.uuid));
    }
}
