package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Timestamp;

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
        createDirectory();
        return DOCUMENTS.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    }

    private void createDirectory() {
        if (!Files.exists(DOCUMENTS)) new File(String.valueOf(DOCUMENTS)).mkdirs();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void watch() {
        try {
            while (true) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    Document document = loadDocument(event);
                    store.store(builder.build(document));
                    store.store(new InvertedIndexEvent(
                            new Timestamp(System.currentTimeMillis()),
                            document.id));
                }
                key.reset();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Document loadDocument(WatchEvent<?> event) {
        Path uuid = (Path) event.context();
        return loader.load(uuid.toString());
    }
}
