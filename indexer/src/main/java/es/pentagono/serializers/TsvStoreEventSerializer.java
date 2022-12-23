package es.pentagono.serializers;

import es.pentagono.Event;
import es.pentagono.EventSerializer;
import es.pentagono.events.InvertedIndexEvent;

public class TsvStoreEventSerializer implements EventSerializer {
    @Override
    public String serialize(Event event) {
        InvertedIndexEvent invertedIndexEvent = (InvertedIndexEvent) event;
        return invertedIndexEvent.ts + "\t" + invertedIndexEvent.uuid + "\n";
    }
}
