package es.pentagono;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FileWatcher {
    private final List<Listener> listeners;
    private boolean started;

    public static FileWatcher of(File file) {
        return new FileWatcher(file);
    }

    private final File file;

    private FileWatcher(File file) {
        this.file = file;
        this.listeners = new ArrayList<>();
    }

    public FileWatcher add(Listener listener) {
        listeners.add(listener);
        return this;
    }

    public FileWatcher start() throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        this.file.toPath().register(watcher, ENTRY_MODIFY);
        this.started = true;
        with(watcher).start();
        return this;
    }

    public void stop() {
        started = false;
    }

    private Thread with(WatchService watcher) {
        return new Thread(() -> waitFor(watcher));
    }

    private void waitFor(WatchService watcher) {
        while (started) {
            try {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    notifyListeners(event.context().toString());
                }
                key.reset();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private  void notifyListeners(String context) {
        for (Listener listener : listeners) listener.changed(context);
    }

    public interface Listener {
        void changed(String context);
    }

}
