package es.pentagono.stores;

import es.pentagono.Metadata;
import es.pentagono.MetadataPersister;
import es.pentagono.MetadataSerializer;
import es.pentagono.Store;

public class MetadataStore implements Store {
    public MetadataPersister persister;
    public MetadataSerializer serializer;


    public MetadataStore(MetadataPersister persister, MetadataSerializer serializer) {
        this.persister = persister;
        this.serializer = serializer;
    }

    @Override
    public void store(Metadata metadata) {
        persister.persist(metadata.title(), serializer.serialize(metadata));
    }
}
