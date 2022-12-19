package es.pentagono.stores;

import es.pentagono.ExtendedMetadata;
import es.pentagono.ExtendedMetadataSerializer;
import es.pentagono.ExtendedMetadataWriter;
import es.pentagono.Store;

public interface ExtendedMetadataStore extends Store {
    void store(ExtendedMetadata extendedMetadata);

    static ExtendedMetadataStore create(ExtendedMetadataWriter writer, ExtendedMetadataSerializer serializer) {
        return extendedMetadata -> writer.write(serializer.serialize(extendedMetadata));
    }
}
