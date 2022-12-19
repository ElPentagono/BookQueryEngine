package es.pentagono.stores;

import es.pentagono.Event;
import es.pentagono.EventPersister;
import es.pentagono.EventSerializer;

public interface EventStore {

    void store(Event event);

    static EventStore create(EventPersister persister, EventSerializer serializer){
        return event -> persister.persist(serializer.serialize(event));
    }
}
