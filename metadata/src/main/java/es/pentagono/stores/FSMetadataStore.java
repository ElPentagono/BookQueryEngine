package es.pentagono.stores;

import es.pentagono.*;
import es.pentagono.events.StoreEvent;

public class FSMetadataStore implements Store {
    public MetadataPersister persister;
    public MetadataSerializer serializer;


    public FSMetadataStore(MetadataPersister persister, MetadataSerializer serializer) {
        this.persister = persister;
        this.serializer = serializer;
    }

    @Override
    public void store(Document document) {
        persister.persist(document);
        persister.persist(new StoreEvent(document.uuid));
    }
}
