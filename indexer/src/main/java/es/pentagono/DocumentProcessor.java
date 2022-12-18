package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.events.InvertedIndexEvent;

public class DocumentProcessor {

    private final DocumentLoader loader;
    private final InvertedIndexBuilder builder;
    private final InvertedIndexStore indexStore;
    private final EventStore eventStore;

    public DocumentProcessor(DocumentLoader loader, InvertedIndexBuilder builder, InvertedIndexStore indexStore, EventStore eventStore) {
        this.loader = loader;
        this.builder = builder;
        this.indexStore = indexStore;
        this.eventStore = eventStore;
    }

    public void process(String uuid) {
        Document document = loader.load(uuid);
        indexStore.store(builder.build(document));
        eventStore.store(new InvertedIndexEvent(
                System.currentTimeMillis(),
                document.id));
    }
}
