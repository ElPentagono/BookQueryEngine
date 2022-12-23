package es.pentagono.tasks;

import es.pentagono.*;
import es.pentagono.events.DownloadEvent;
import es.pentagono.events.StoreEvent;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

public class DownloadTask implements Task {

    private Source source;
    private DocumentStore documentStore;
    private EventStore eventStore;


    public DownloadTask from(Source source) {
        this.source = source;
        return this;
    }

    public DownloadTask into(DocumentStore documentStore, EventStore eventStore) {
        this.documentStore = documentStore;
        this.eventStore = eventStore;
        return this;
    }

    @Override
    public void execute() {
        Iterator<Event> events = source.all();
        try {
            while (events.hasNext()) {
                DownloadEvent event = (DownloadEvent) events.next();
                if (isStored(event.source, Md5(event.content))) continue;
                eventStore.store(new StoreEvent(
                        event.ts,
                        event.source,
                        documentStore.store(new Document(event.source, event.metadata, event.content)),
                        Md5(event.content))
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isStored(String source, String md5) throws IOException {
        if (!Files.exists(Paths.get(Configuration.getProperty("datalake") + "/events/crawler.log"))) return false;
        return Files.lines(Paths.get(Configuration.getProperty("datalake") + "/events/crawler.log"))
                .map(line -> line.split("\t"))
                .anyMatch(row -> row[0].equals(source) && row[2].equals(md5));
    }

    private static String Md5(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return String.format("%032X", new BigInteger(1, md.digest(content.getBytes())));
    }
}
