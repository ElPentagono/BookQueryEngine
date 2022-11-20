package es.pentagono.stores;

import es.pentagono.InvertedIndex;
import es.pentagono.InvertedIndexPersister;
import es.pentagono.InvertedIndexSerializer;
import es.pentagono.InvertedIndexStore;

public class FileSystemInvertedIndexStore implements InvertedIndexStore {
    InvertedIndexSerializer serializer;
    InvertedIndexPersister persister;

    public FileSystemInvertedIndexStore(InvertedIndexSerializer invertedIndexSerializer, InvertedIndexPersister invertedIndexPersister) {
        this.serializer = invertedIndexSerializer;
        this.persister = invertedIndexPersister;
    }

    @Override
    public void store(InvertedIndex invertedIndex) {
        persister.persist(serializer.serialize(invertedIndex));
    }
}
