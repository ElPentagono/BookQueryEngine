package es.pentagono;

import es.pentagono.deserializer.GsonMetadataDeserializer;
import es.pentagono.metadatas.DatalakeMetadata;
import es.pentagono.metadatas.DatamartMetadata;
import es.pentagono.readers.FSMetadataReader;
import es.pentagono.stores.SQLMetadataStore;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        GsonMetadataDeserializer deserializer = new GsonMetadataDeserializer();
        Store store = new SQLMetadataStore();
        File file = new File(System.getenv("DATALAKE") + "/documents");
        Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(filename -> {
            DatalakeMetadata metadata = (DatalakeMetadata) new FSMetadataReader(deserializer).read(filename.getName());
            store.store(
                    new DatamartMetadata(
                            metadata.title(),
                            metadata.author(),
                            metadata.language(),
                            metadata.releaseDate()
                    ),
                    filename.getName()
            );
        });

        FileWatcher.of(file).add((String f) -> {
            DatalakeMetadata metadata = (DatalakeMetadata) new FSMetadataReader(deserializer).read(f);
            store.store(new DatamartMetadata(
                            metadata.title(), metadata.author(),
                            metadata.language(), metadata.releaseDate()),
                    f);
        }).start();

    }
}
