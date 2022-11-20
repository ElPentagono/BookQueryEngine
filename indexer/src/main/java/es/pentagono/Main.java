package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.deserializer.FileSystemDocumentLoader;
import es.pentagono.persisters.FileSystemInvertedIndexPersister;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.serializers.TsvInvertedIndexSerializer;
import es.pentagono.stores.FileSystemInvertedIndexStore;
import es.pentagono.tokenizers.GutenbergTokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        DatalakeWatcher watcher = new DatalakeWatcher(
                new FileSystemDocumentLoader(),
                new InvertedIndexBuilder(new GutenbergTokenizer()),
                new FileSystemInvertedIndexStore(
                        new TsvInvertedIndexSerializer(),
                        new TsvEventSerializer(),
                        new FileSystemInvertedIndexPersister()
                ));
        moveToTemp();
        watcher.watch();
    }

    private static void moveToTemp() {
        try {
            Path tmpdir = Files.createTempDirectory(System.getenv("java.io.tmpdir"));
            moveFiles(
                    new File(String.valueOf(Paths.get(System.getenv("DATALAKE") + "/documents"))).toPath(),
                    tmpdir);
            moveFiles(
                    tmpdir,
                    new File(String.valueOf(Paths.get(System.getenv("DATALAKE") + "/documents"))).toPath());
            tmpdir.toFile().deleteOnExit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void moveFiles(Path origin, Path target) throws IOException {
        Files.move(origin, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
