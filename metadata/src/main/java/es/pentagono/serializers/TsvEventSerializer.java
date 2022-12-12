package es.pentagono.serializers;

import es.pentagono.Event;
import es.pentagono.EventSerializer;
import es.pentagono.events.StoreEvent;

public class TsvEventSerializer implements EventSerializer {
    @Override
    public String serializeConfig(Event event) {
        StoreEvent storeEvent = (StoreEvent) event;
        return storeEvent.filename;
    }

    @Override
    public String serializeDatalake(Event event) {
        StoreEvent storeEvent = (StoreEvent) event;
        return storeEvent.ts + "\t" + storeEvent.filename + "METADATA DATAMART" + "\t" + "DOCUMENT ADDED" + "\n";
    }
}
