package es.pentagono.stores;

import es.pentagono.Metadata;
import es.pentagono.MetadataPersister;
import es.pentagono.MetadataSerializer;
import es.pentagono.Store;
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
    public void store(Metadata metadata, String uuid) {
        persister.persist(uuid, serializer.serialize(metadata));
        persister.persist(new StoreEvent(uuid));
    }
}
