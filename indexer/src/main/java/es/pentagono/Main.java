package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.deserializer.FSDocumentLoader;
import es.pentagono.persisters.FSInvertedIndexPersister;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.serializers.TsvInvertedIndexSerializer;
import es.pentagono.stores.FSInvertedIndexStore;
import es.pentagono.tokenizers.GutenbergTokenizer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        DocumentProcessor docProcessor = new DocumentProcessor(
                new FSDocumentLoader(),
                new InvertedIndexBuilder(
                        new GutenbergTokenizer()
                ),
                new FSInvertedIndexStore(
                        new TsvInvertedIndexSerializer(),
                        new TsvEventSerializer(),
                        new FSInvertedIndexPersister()
                )
        );
        new Updater(docProcessor).update();
        FileSystemEntityWatcher
                .of(new File(System.getenv("DATALAKE") + "/documents")).
                addListener(docProcessor::process).
                watch();
    }
}
