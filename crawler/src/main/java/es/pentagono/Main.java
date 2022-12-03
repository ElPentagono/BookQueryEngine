package es.pentagono;

import es.pentagono.serializers.JsonMetadataSerializer;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.sources.GutenbergSource;
import es.pentagono.stores.FileSystemDocumentStore;
import es.pentagono.tasks.DownloadTask;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(new Timer());
        scheduler.add(new DownloadTask()
                .from(new GutenbergSource())
                .into(new FileSystemDocumentStore(new JsonMetadataSerializer(), new TsvEventSerializer())));
        scheduler.start();
    }
}
