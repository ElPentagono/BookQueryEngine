package es.pentagono.crawler;

import es.pentagono.crawler.serializers.JsonMetadataSerializer;
import es.pentagono.crawler.serializers.TsvEventSerializer;
import es.pentagono.crawler.sources.GutenbergSource;
import es.pentagono.crawler.stores.FileSystemDocumentStore;
import es.pentagono.crawler.tasks.DownloadTask;

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
