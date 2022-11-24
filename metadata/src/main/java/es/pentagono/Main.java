package es.pentagono;

import es.pentagono.deserializer.GsonMetadataDeserializer;
import es.pentagono.metadatas.DatalakeMetadata;
import es.pentagono.metadatas.DatamartMetadata;
import es.pentagono.persisters.MetadataDatamartPersister;
import es.pentagono.readers.FSMetadataReader;
import es.pentagono.serializers.JsonMetadataSerializer;
import es.pentagono.stores.MetadataStore;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GsonMetadataDeserializer deserializer = new GsonMetadataDeserializer();

        MetadataDatamartPersister persister = new MetadataDatamartPersister();
        MetadataSerializer serializer = new JsonMetadataSerializer();
        MetadataStore store = new MetadataStore(persister, serializer);
        File file = new File("C:/Users/Jose Juan/IdeaProjects/BookQueryEngine/datalake/documents");
        File[] files = file.listFiles();
        for (File filename : files) {
            DatalakeMetadata metadata = (DatalakeMetadata) new FSMetadataReader(deserializer)
                    .read(filename.getName());
            store.store(new DatamartMetadata(
                            metadata.title(),
                            metadata.author(),
                            metadata.language(),
                            metadata.releaseDate()),
                    filename.getName()
            );
        }

        FileWatcher.of(file).add((String f) -> {
            DatalakeMetadata metadata = (DatalakeMetadata) new FSMetadataReader(deserializer).read(f);
            store.store(new DatamartMetadata(
                            metadata.title(), metadata.author(),
                            metadata.language(), metadata.releaseDate()),
                    f);
        }).start();

    }
}
