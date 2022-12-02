package es.pentagono;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FileSystemEntityWatcher {

    private static final List<Listener> listeners = new ArrayList<>();
    private static WatchService watchService;
    private static WatchKey key;
    private final File file;

    public static FileSystemEntityWatcher of(File file) {
        return new FileSystemEntityWatcher(file);
    }

    private FileSystemEntityWatcher(File file) {
        try {
            this.file = file;
            watchService = FileSystems.getDefault().newWatchService();
            key = registerDirectory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FileSystemEntityWatcher addListener(Listener listener) {
        listeners.add(listener);
        return this;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void watch() {
        try {
            while (true) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    key.reset();
                    notifyListeners(((Path) event.context()).toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private WatchKey registerDirectory() throws IOException {
        createDirectory();
        return this.file.toPath().register(watchService, ENTRY_MODIFY);
    }

    private void createDirectory() {
        if (!Files.exists(this.file.toPath())) new File(String.valueOf(this.file)).mkdirs();
    }

    private void notifyListeners(String uuid) {
        for (Listener listener : listeners) listener.change(uuid);
    }
}
