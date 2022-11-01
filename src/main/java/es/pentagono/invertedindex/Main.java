package es.pentagono.invertedindex;

import es.pentagono.Document;
import es.pentagono.InvertedIndex;
import es.pentagono.invertedindex.builders.InvertedIndexBuilder;
import es.pentagono.invertedindex.deserializer.FileSystemDocumentLoader;
import es.pentagono.invertedindex.persisters.FileSystemInvertedIndexPersister;
import es.pentagono.invertedindex.serializers.TsvInvertedIndexSerializer;
import es.pentagono.invertedindex.stores.FileSystemInvertedIndexStore;
import es.pentagono.invertedindex.tokenizers.GutenbergTokenizer;

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
