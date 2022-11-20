package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;

import java.io.IOException;
import java.nio.file.*;

public class DatalakeWatcher {
    private final Path DOCUMENTS = Paths.get(System.getenv("DATALAKE") + "/documents");
    private final WatchService watchService;
    private final WatchKey key;
    private final DocumentLoader loader;
    private final InvertedIndexBuilder builder;
    private final InvertedIndexStore store;

    public DatalakeWatcher(DocumentLoader documentLoader, InvertedIndexBuilder invertedIndexBuilder, InvertedIndexStore invertedIndexStore) {
        try {
            this.loader = documentLoader;
            this.builder = invertedIndexBuilder;
            this.store = invertedIndexStore;
            this.watchService = FileSystems.getDefault().newWatchService();
            this.key = registerDirectory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private WatchKey registerDirectory() throws IOException {
        return DOCUMENTS.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void watch() {
        try {
            while (true) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    String uuid = (String) event.context();
                    Document document = loader.load(uuid);
                    InvertedIndex invertedIndex = builder.build(document);
                    store.store(invertedIndex);
                }
                key.reset();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
