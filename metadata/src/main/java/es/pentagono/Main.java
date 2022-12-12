package es.pentagono;

import es.pentagono.deserializer.GsonMetadataDeserializer;
import es.pentagono.metadatas.DatalakeMetadata;
import es.pentagono.metadatas.DatamartMetadata;
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
            DatalakeMetadata metadata = (DatalakeMetadata) new FSMetadataReader(deserializer).read(filename.getName());
            store.store(new Document(filename.getName(), sqlMetadataSerializer.serialize(
                    new DatamartMetadata(
                            metadata.title(),
                            metadata.author(),
                            metadata.language(),
                            metadata.releaseDate()
                    )
            )));
        });

        FileWatcher.of(file).add((String f) -> {
            DatalakeMetadata metadata = (DatalakeMetadata) new FSMetadataReader(deserializer).read(f);
            store.store(new Document(f, sqlMetadataSerializer.serialize(new DatamartMetadata(
                            metadata.title(), metadata.author(),
                            metadata.language(), metadata.releaseDate())
            )));
        }).start();

    }
}
