package es.pentagono.crawler.tasks;

import es.pentagono.Document;
import es.pentagono.crawler.DocumentStore;
import es.pentagono.crawler.EventPersister;
import es.pentagono.crawler.Source;
import es.pentagono.crawler.Task;
import es.pentagono.crawler.events.DownloadEvent;
import es.pentagono.crawler.events.StoreEvent;
import es.pentagono.invertedindex.builders.MetadataBuilder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DownloadTask implements Task {

    private final MetadataBuilder builder = new MetadataBuilder(); //TODO change name into CapitalCase?
    private final int id;
    private final Source source;
    private final DocumentStore store;
    private final EventPersister persister;

    public DownloadTask(int id, Source source, DocumentStore store, EventPersister persister) {
        this.id = id;
        this.source = source;
        this.store = store;
        this.persister = persister;
    }

    @Override
    public void execute() {
        try {
            DownloadEvent event = source.readBook(id);
            String content = event.content;
            String source = event.source;
            String md5 = Md5(content);
            if (alreadyStored(source, md5)) return;
            String uuid = store.store(new Document(source, builder.build(event.metadata), content));
            persister.persist(new StoreEvent(event.ts, source, uuid, md5)); //TODO
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean alreadyStored(String source, String md5) {
        Scanner sc = new Scanner(""); //TODO add path to project
        while (sc.hasNextLine()) {
            String[] values = sc.nextLine().trim().split("\t");
            if (source.equals(values[1]) && md5.equals(values[3])) return true;
        }
        sc.close();
        return false;
    }

    private static String Md5(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return String.format("%032X", new BigInteger(1, md.digest(content.getBytes())));
    }
}
