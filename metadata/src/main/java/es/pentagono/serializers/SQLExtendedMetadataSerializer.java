package es.pentagono.serializers;

import es.pentagono.ExtendedMetadata;
import es.pentagono.ExtendedMetadataSerializer;

import java.time.LocalDate;

public class SQLExtendedMetadataSerializer implements ExtendedMetadataSerializer {

    @Override
    public String serialize(ExtendedMetadata extendedMetadata) {
        return "'\"" + extendedMetadata.uuid + "\"','"
                + getValue(extendedMetadata.metadata.title) + "','"
                + getValue(extendedMetadata.metadata.author) + "','"
                + getValue(extendedMetadata.metadata.language) + "','"
                + getValue(extendedMetadata.metadata.releaseDate) + "','"
                + extendedMetadata.wordsCount + "'";
    }

    private String getValue(String metadataField) {
        return (metadataField == null) ? "NULL" : metadataField.replaceAll("'","");
    }

    private String getValue(LocalDate releaseDate) {
        return (releaseDate == null) ? "NULL" : releaseDate + "";
    }
}
