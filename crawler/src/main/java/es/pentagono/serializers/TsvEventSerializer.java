package es.pentagono.serializers;

import es.pentagono.EventSerializer;
import es.pentagono.Event;
import es.pentagono.events.StoreEvent;

public class TsvEventSerializer implements EventSerializer {
    @Override
    public String serialize(Event event) {
        StoreEvent downloadEvent = (StoreEvent) event;
        return downloadEvent.ts.getNanos() + "\t" + downloadEvent.source + "\t" + downloadEvent.uuid + "\t" + downloadEvent.md5 + "\n";
    }
}
