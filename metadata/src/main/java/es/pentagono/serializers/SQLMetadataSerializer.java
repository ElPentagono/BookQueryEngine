package es.pentagono.serializers;

import es.pentagono.ExtendedMetadata;
import es.pentagono.Metadata;
import es.pentagono.MetadataSerializer;

import java.time.LocalDate;

public class SQLMetadataSerializer implements MetadataSerializer {
    @Override
    public String serialize(Metadata metadata) {
        return "'" + getValue(metadata.title()) + "','"
                + getValue(metadata.author()) + "','"
                + getValue(metadata.language()) + "','"
                + metadata.releaseDate() + "'";
    }

    @Override
    public String serialize(ExtendedMetadata extendedMetadata) {
        return "'" + getValue(extendedMetadata.metadata.title()) + "','"
                + getValue(extendedMetadata.metadata.author()) + "','"
                + getValue(extendedMetadata.metadata.language()) + "','"
                + getValue(extendedMetadata.metadata.releaseDate()) + "','"
                + extendedMetadata.numberOfWords() + "'";
    }

    private String getValue(String metadataField) {
        return (metadataField == null) ? "NULL" : metadataField.replaceAll("'","");
    }

    private String getValue(LocalDate releaseDate) {
        return (releaseDate == null) ? "NULL" : releaseDate + "";
    }
}
