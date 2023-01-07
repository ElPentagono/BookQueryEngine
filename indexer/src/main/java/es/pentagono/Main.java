package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.loaders.FSDocumentLoader;
import es.pentagono.persisters.FSEventPersister;
import es.pentagono.stores.EventStore;
import es.pentagono.stores.InvertedIndexStore;
import es.pentagono.writers.FSInvertedIndexWriter;
import es.pentagono.serializers.TsvInvertedIndexSerializer;
import es.pentagono.serializers.TsvStoreEventSerializer;
import es.pentagono.tokenizers.GutenbergTokenizer;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        DocumentProcessor docProcessor = new DocumentProcessor(
                new FSDocumentLoader(),
                new InvertedIndexBuilder(new GutenbergTokenizer()),
                InvertedIndexStore.create(new FSInvertedIndexWriter(), new TsvInvertedIndexSerializer()),
                EventStore.create(new FSEventPersister(), new TsvStoreEventSerializer())
        );

        Scheduler scheduler = new Scheduler(new Timer());
        scheduler.add(new UpdateTask(docProcessor));
        scheduler.start();

    }
}
