package es.pentagono;

import es.pentagono.deserializer.GsonMetadataDeserializer;
import es.pentagono.readers.FSMetadataReader;
import es.pentagono.serializers.SQLMetadataSerializer;
import es.pentagono.stores.SQLMetadataStore;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        SQLMetadataSerializer sqlMetadataSerializer = new SQLMetadataSerializer();
        GsonMetadataDeserializer deserializer = new GsonMetadataDeserializer();
        Store store = new SQLMetadataStore();
        File file = new File("/app/datalake" + "/documents"); // System.getenv("DATALAKE") + "/documents"
        while (!file.exists()) {}
        Arrays.stream(file.listFiles()).forEach(filename -> {
            Metadata metadata = new FSMetadataReader(deserializer).read(filename.getName());
            store.store(new Document(filename.getName(), sqlMetadataSerializer.serialize(
                    new Metadata(
                            metadata.title(),
                            metadata.author(),
                            metadata.language(),
                            metadata.releaseDate(),
                            filename.getName()
                    )
            )));
        });

        FileWatcher.of(file).add((String documentId) -> {
            Metadata metadata = new FSMetadataReader(deserializer).read(documentId);
            store.store(new Document(documentId, sqlMetadataSerializer.serialize(new Metadata(
                            metadata.title(), metadata.author(),
                            metadata.language(), metadata.releaseDate(), documentId)
            )));
        }).start();

    }
}
