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

    private final MetadataBuilder Builder = new MetadataBuilder();
    private Source source;
    private DocumentStore store;


    public DownloadTask from(Source source) {
        this.source = source;
        return this;
    }

    public DownloadTask into(DocumentStore store) {
        this.store = store;
        return this;
    }

    @Override
    public void execute() {
        Iterator<Event> events = source.all();
        try {
            while (events.hasNext()) {
                DownloadEvent event = (DownloadEvent) events.next();
                if (isStored(event.source, Md5(event.content))) continue;
                store.store(new StoreEvent(
                        event.ts,
                        event.source,
                        store.store(new Document(event.source, Builder.build(event.metadata), event.content)),
                        Md5(event.content))
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isStored(String source, String md5) throws IOException {
        if (!Files.exists(Paths.get(System.getenv("DATALAKE") + "/events/updates.log"))) return false;
        return Files.lines(Paths.get(System.getenv("DATALAKE") + "/events/updates.log"))
                .map(line -> line.split("\t"))
                .anyMatch(row -> row[1].equals(source) && row[3].equals(md5));
    }

    private static String Md5(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return String.format("%032X", new BigInteger(1, md.digest(content.getBytes())));
    }
}
