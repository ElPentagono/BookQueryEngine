package es.pentagono.stores;

import es.pentagono.Event;
import es.pentagono.EventPersister;
import es.pentagono.EventSerializer;
import es.pentagono.EventStore;
import es.pentagono.persisters.FSEventPersister;

public class FSEventStore implements EventStore {

    private final EventPersister persister = new FSEventPersister();
    private final EventSerializer serializer;

    public FSEventStore(EventSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void store(Event event) {
        persister.persist(serializer.serialize(event));
    }
}
