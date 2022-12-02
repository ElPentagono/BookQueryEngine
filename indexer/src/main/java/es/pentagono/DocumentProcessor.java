package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;

import java.sql.Timestamp;

public class DocumentProcessor {
    private final DocumentLoader loader;
    private final InvertedIndexBuilder builder;
    private final InvertedIndexStore store;
    public DocumentProcessor(DocumentLoader loader, InvertedIndexBuilder builder, InvertedIndexStore store) {
        this.loader = loader;
        this.builder = builder;
        this.store = store;
    }
    public void process(String uuid) {
        Document document = loader.load(uuid);
        store.store(builder.build(document));
        store.store(new InvertedIndexEvent(
                new Timestamp(System.currentTimeMillis()),
                document.id));
    }
}
