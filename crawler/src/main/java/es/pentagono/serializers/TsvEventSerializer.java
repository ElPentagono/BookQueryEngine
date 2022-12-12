package es.pentagono.serializers;

import es.pentagono.EventSerializer;
import es.pentagono.Event;
import es.pentagono.events.StoreEvent;

public class TsvEventSerializer implements EventSerializer {
    @Override
    public String serializeConfig(Event event) {
        StoreEvent downloadEvent = (StoreEvent) event;
        return downloadEvent.source + "\t"
                + downloadEvent.uuid + "\t"
                + downloadEvent.md5 + "\n";
    }

    @Override
    public String serializeDatalake(Event event) {
        StoreEvent downloadEvent = (StoreEvent) event;
        return downloadEvent.ts.getTime() + "\t"
                + downloadEvent.uuid + "\t"
                + "DOCUMENTS DATALAKE" + "\t"
                + "DOCUMENT ADDED" + "\n";
    }
}
