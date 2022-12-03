package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.deserializer.FileSystemDocumentLoader;
import es.pentagono.persisters.FileSystemInvertedIndexPersister;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.serializers.TsvInvertedIndexSerializer;
import es.pentagono.stores.FileSystemInvertedIndexStore;
import es.pentagono.tokenizers.GutenbergTokenizer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        DocumentProcessor docProcessor = new DocumentProcessor(
                new FileSystemDocumentLoader(),
                new InvertedIndexBuilder(
                        new GutenbergTokenizer()
                ),
                new FileSystemInvertedIndexStore(
                        new TsvInvertedIndexSerializer(),
                        new TsvEventSerializer(),
                        new FileSystemInvertedIndexPersister()
                )
        );
        new Updater(docProcessor).update();
        FileSystemEntityWatcher
                .of(new File(System.getenv("DATALAKE") + "/documents")).
                addListener(docProcessor::process).
                watch();
    }
}
