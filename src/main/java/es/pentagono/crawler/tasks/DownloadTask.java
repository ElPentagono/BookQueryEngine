package es.pentagono.crawler.tasks;

import es.pentagono.Document;
import es.pentagono.crawler.events.DownloadEvent;
import es.pentagono.crawler.sources.GutenbergSource;
import es.pentagono.crawler.Task;
import es.pentagono.crawler.serializers.JsonMetadataSerializer;
import es.pentagono.crawler.stores.FSDocumentStore;
import es.pentagono.invertedindex.builders.MetadataBuilder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DownloadTask implements Task {
    private final int id;
    private final MetadataBuilder builder = new MetadataBuilder();

    public DownloadTask(int id) {
        this.id = id;
    }

    @Override
    public void execute() {
        try {
            DownloadEvent event = new GutenbergSource().readBook(id);
            FSDocumentStore store = new FSDocumentStore(new JsonMetadataSerializer());
            //TODO check if book exists
            String content = event.content;
            String uuid = store.store(new Document(event.source, builder.build(event.metadata), content));
            String md5 = Md5(content);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String Md5(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return String.format("%032X", new BigInteger(1, md.digest(content.getBytes())));
    }
}
