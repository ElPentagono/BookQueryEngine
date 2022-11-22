package es.pentagono;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;

public class DatalakeWatcher {
    private final Path DOCUMENTS = Paths.get(System.getenv("DATALAKE") + "/documents");
    private final List<Observer> observers;
    private final WatchService watchService;
    private final WatchKey key;

    public DatalakeWatcher() {
        try {
            observers = new LinkedList<>();
            this.watchService = FileSystems.getDefault().newWatchService();
            this.key = registerDirectory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(String uuid) {
        for (Observer observer : observers) observer.onSomething(uuid);
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
                    key.reset();
                    notifyObservers(((Path) event.context()).toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
