package es.pentagono;

import es.pentagono.events.StoreEvent;
import es.pentagono.persisters.FSEventPersister;
import es.pentagono.serializers.SQLExtendedMetadataSerializer;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.stores.EventStore;
import es.pentagono.stores.ExtendedMetadataStore;
import es.pentagono.writers.SQLExtendedExtendedMetadataWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class UpdateTask implements Task{
    public final File file;
    public final MetadataReader reader;

    public UpdateTask(File file, MetadataReader reader) {
        this.file = file;
        this.reader = reader;
    }

    @Override
    public void execute() {
        Arrays.stream(file.listFiles()).map(File::getName).forEach(uuid -> processBook(reader, uuid));
    }

    private static void processBook(MetadataReader reader, String uuid) {
        try {
            if (isProcessed(uuid)) return;
            ExtendedMetadataStore.create(new SQLExtendedExtendedMetadataWriter(), new SQLExtendedMetadataSerializer())
                    .store(new ExtendedMetadata(uuid, reader.read(uuid)));
            EventStore.create(new FSEventPersister(), new TsvEventSerializer())
                    .store(new StoreEvent(System.currentTimeMillis(), uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isProcessed(String uuid) throws IOException {
        if (!Files.exists(Path.of(Configuration.getProperty("datalake") + "/events/metadata.log"))) return false;
        return Files.readAllLines(Paths.get(Configuration.getProperty("datalake") + "/events/metadata.log")).stream()
                .anyMatch(s -> s.contains(uuid));
    }
}
