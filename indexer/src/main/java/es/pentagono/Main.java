package es.pentagono;

import es.pentagono.builders.InvertedIndexBuilder;
import es.pentagono.deserializer.FileSystemDocumentLoader;
import es.pentagono.persisters.FileSystemInvertedIndexPersister;
import es.pentagono.serializers.TsvInvertedIndexSerializer;
import es.pentagono.stores.FileSystemInvertedIndexStore;
import es.pentagono.tokenizers.GutenbergTokenizer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        FileSystemDocumentLoader loader = new FileSystemDocumentLoader();
        Document document = loader.load(getIdFromDatalake());
        InvertedIndexBuilder builder = new InvertedIndexBuilder(new GutenbergTokenizer());
        InvertedIndex invertedIndex = builder.build(document);
        FileSystemInvertedIndexStore store = new FileSystemInvertedIndexStore(
                new FileSystemInvertedIndexPersister(),
                new TsvInvertedIndexSerializer());
        store.store(invertedIndex);
    }

    private static String getIdFromDatalake() {
        if (!Files.exists(Paths.get(System.getenv("DATALAKE") + "/documents"))) return null;
        File f = new File(System.getenv("DATALAKE") + "/documents");
        return Objects.requireNonNull(f.listFiles())[1].getName();
    }
}
