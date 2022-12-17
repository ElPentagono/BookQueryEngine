package es.pentagono.serializers;

import es.pentagono.EventSerializer;
import es.pentagono.Event;
import es.pentagono.events.StoreEvent;

public class TsvEventSerializer implements EventSerializer {
    @Override
    public String serialize(Event event) {
        StoreEvent storeEvent = (StoreEvent) event;
        return storeEvent.ts.getTime() + "\t"
                + storeEvent.source + "\t"
                + storeEvent.uuid + "\t"
                + storeEvent.md5 + "\n";
    }
}
