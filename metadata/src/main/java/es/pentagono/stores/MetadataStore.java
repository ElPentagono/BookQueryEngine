package es.pentagono.stores;

import es.pentagono.*;
import es.pentagono.events.StoreEvent;

public class MetadataStore implements Store {
    public MetadataPersister persister;
    public MetadataSerializer serializer;


    public MetadataStore(MetadataPersister persister, MetadataSerializer serializer) {
        this.persister = persister;
        this.serializer = serializer;
    }

    @Override
    public void store(Metadata metadata, String uuid) {
        persister.persist(uuid, serializer.serialize(metadata));
        persister.persist(new StoreEvent(uuid));
    }
}
