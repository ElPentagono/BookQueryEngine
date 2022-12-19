package es.pentagono.stores;

import es.pentagono.Event;
import es.pentagono.EventPersister;
import es.pentagono.EventSerializer;
import es.pentagono.Store;

public interface EventStore extends Store {
    void store(Event event);

    static EventStore create(EventPersister persister, EventSerializer serializer) {
        return event -> persister.persist(serializer.serialize(event));
    }
}
