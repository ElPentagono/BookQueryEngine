package es.pentagono.stores;

import es.pentagono.Event;
import es.pentagono.EventPersister;
import es.pentagono.EventSerializer;
import es.pentagono.EventStore;

public class FSEventStore implements EventStore {
    private final EventPersister persister;
    private final EventSerializer serializer;

    public FSEventStore(EventPersister persister, EventSerializer serializer) {
        this.persister = persister;
        this.serializer = serializer;
    }

    @Override
    public void store(Event event) {
        persister.persist(serializer.serialize(event));
    }
}
