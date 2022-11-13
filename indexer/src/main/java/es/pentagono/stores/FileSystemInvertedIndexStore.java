package es.pentagono.stores;

import es.pentagono.InvertedIndex;
import es.pentagono.InvertedIndexPersister;
import es.pentagono.InvertedIndexSerializer;
import es.pentagono.InvertedIndexStore;

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
