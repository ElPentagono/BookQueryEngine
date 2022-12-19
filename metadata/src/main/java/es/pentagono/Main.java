package es.pentagono;

import es.pentagono.deserializer.GsonMetadataDeserializer;
import es.pentagono.events.StoreEvent;
import es.pentagono.persisters.FSEventPersister;
import es.pentagono.writers.SQLExtendedExtendedMetadataWriter;
import es.pentagono.readers.FSMetadataReader;
import es.pentagono.serializers.SQLExtendedMetadataSerializer;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.stores.EventStore;
import es.pentagono.stores.ExtendedMetadataStore;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        FSMetadataReader reader = new FSMetadataReader(new GsonMetadataDeserializer());
        File file = new File(Configuration.getProperty("datalake") + "/documents");
        while (!file.exists()) {}
        Arrays.stream(file.listFiles())
                .map(File::getName)
                .forEach(uuid -> {
                    ExtendedMetadataStore.create(new SQLExtendedExtendedMetadataWriter(), new SQLExtendedMetadataSerializer())
                                    .store(new ExtendedMetadata(uuid, reader.read(uuid)));
                    EventStore.create(new FSEventPersister(), new TsvEventSerializer())
                                    .store(new StoreEvent(System.currentTimeMillis(), uuid));
                        }
                );
        FileWatcher.of(file).add((String uuid) -> {
            ExtendedMetadataStore.create(new SQLExtendedExtendedMetadataWriter(), new SQLExtendedMetadataSerializer())
                    .store(new ExtendedMetadata(uuid, reader.read(uuid)));
            EventStore.create(new FSEventPersister(), new TsvEventSerializer())
                    .store(new StoreEvent(System.currentTimeMillis(), uuid));
        }).start();
    }
}
