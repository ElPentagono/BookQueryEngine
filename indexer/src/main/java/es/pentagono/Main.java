package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.loaders.FSDocumentLoader;
import es.pentagono.persisters.FSEventPersister;
import es.pentagono.writers.FSInvertedIndexWriter;
import es.pentagono.serializers.TsvInvertedIndexSerializer;
import es.pentagono.serializers.TsvStoreEventSerializer;
import es.pentagono.stores.FSEventStore;
import es.pentagono.stores.FSInvertedIndexStore;
import es.pentagono.tokenizers.GutenbergTokenizer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        DocumentProcessor docProcessor = new DocumentProcessor(
                new FSDocumentLoader(),
                new InvertedIndexBuilder(new GutenbergTokenizer()),
                new FSInvertedIndexStore(
                        new FSInvertedIndexWriter(),
                        new TsvInvertedIndexSerializer()
                ),
                new FSEventStore(
                        new FSEventPersister(),
                        new TsvStoreEventSerializer()
                )
        );
        new Updater(docProcessor).update();
        FSEntityWatcher
                .of(new File(Configuration.getProperty("datalake") + "/documents"))
                .addListener(docProcessor::process)
                .watch();
    }
}
