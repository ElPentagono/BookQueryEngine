package es.pentagono;

import es.pentagono.serializers.JsonMetadataSerializer;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.sources.GutenbergSource;
import es.pentagono.stores.FSDocumentStore;
import es.pentagono.stores.FSEventStore;
import es.pentagono.tasks.DownloadTask;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(new Timer());
        scheduler.add(new DownloadTask()
                            .from(new GutenbergSource())
                            .into(new FSDocumentStore(new JsonMetadataSerializer()),
                                    new FSEventStore(new TsvEventSerializer())
                            ));
        scheduler.start();
    }
}
