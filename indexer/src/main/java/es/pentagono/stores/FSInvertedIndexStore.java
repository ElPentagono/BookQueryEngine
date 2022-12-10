package es.pentagono.stores;

import es.pentagono.*;

public class FSInvertedIndexStore implements InvertedIndexStore {
    private final InvertedIndexSerializer serializer;
    private final EventSerializer eventSerializer;
    private final InvertedIndexPersister persister;

    public FSInvertedIndexStore(InvertedIndexSerializer invertedIndexSerializer, EventSerializer eventSerializer, InvertedIndexPersister invertedIndexPersister) {
        this.serializer = invertedIndexSerializer;
        this.eventSerializer = eventSerializer;
        this.persister = invertedIndexPersister;
    }

    @Override
    public void store(InvertedIndex invertedIndex) {
        persister.persistConfig(serializer.serialize(invertedIndex));
    }

    @Override
    public void store(Event event) {
        persister.persistConfig(eventSerializer.serializeConfig(event));
        persister.persistDatalake(eventSerializer.serializeDatalake(event));
    }
}
