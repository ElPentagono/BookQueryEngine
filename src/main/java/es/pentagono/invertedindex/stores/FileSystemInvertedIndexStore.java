package es.pentagono.invertedindex.stores;

import es.pentagono.InvertedIndex;
import es.pentagono.invertedindex.InvertedIndexPersister;
import es.pentagono.invertedindex.InvertedIndexSerializer;
import es.pentagono.invertedindex.InvertedIndexStore;

public class FileSystemInvertedIndexStore implements InvertedIndexStore {
    InvertedIndexPersister persister;
    InvertedIndexSerializer serializer;

    public FileSystemInvertedIndexStore(InvertedIndexPersister invertedIndexPersister, InvertedIndexSerializer invertedIndexSerializer) {
        this.persister = invertedIndexPersister;
        this.serializer = invertedIndexSerializer;
    }

    @Override
    public void store(InvertedIndex invertedIndex) {
        persister.persist(serializer.serialize(invertedIndex));
    }
}
