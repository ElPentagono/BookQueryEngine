package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.deserializer.FileSystemDocumentLoader;
import es.pentagono.persisters.FileSystemInvertedIndexPersister;
import es.pentagono.serializers.TsvInvertedIndexSerializer;
import es.pentagono.stores.FileSystemInvertedIndexStore;
import es.pentagono.tokenizers.GutenbergTokenizer;

public class Main {
    public static void main(String[] args) {
        DatalakeWatcher watcher = new DatalakeWatcher(
                new FileSystemDocumentLoader(),
                new InvertedIndexBuilder(new GutenbergTokenizer()),
                new FileSystemInvertedIndexStore(
                        new TsvInvertedIndexSerializer(),
                        new FileSystemInvertedIndexPersister()
                ));
        watcher.watch();
    }
}
