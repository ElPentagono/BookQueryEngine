package es.pentagono;

public interface MetadataSerializer {
    String serialize(Metadata metadata);

    String serialize(ExtendedMetadata extendedMetadata);
}
