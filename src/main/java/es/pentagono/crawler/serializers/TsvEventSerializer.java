package es.pentagono.crawler.serializers;

import es.pentagono.crawler.Event;
import es.pentagono.crawler.EventSerializer;
import es.pentagono.crawler.events.StoreEvent;

public class TsvEventSerializer implements EventSerializer {
    @Override
    public String serialize(Event event) {
        StoreEvent downloadEvent = (StoreEvent) event;
        return downloadEvent.ts.getNanos() + "\t" + downloadEvent.source + "\t" + downloadEvent.uuid + "\t" + downloadEvent.md5 + "\n";
    }
}
