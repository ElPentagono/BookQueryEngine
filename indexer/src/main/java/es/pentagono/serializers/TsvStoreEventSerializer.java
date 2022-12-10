package es.pentagono.serializers;

import es.pentagono.Event;
import es.pentagono.EventSerializer;
import es.pentagono.InvertedIndexEvent;

public class TsvStoreEventSerializer implements EventSerializer {
    @Override
    public String serializeConfig(Event event) {
        InvertedIndexEvent invertedIndexEvent = (InvertedIndexEvent) event;
        return invertedIndexEvent.uuid() + "\n";
    }

    @Override
    public String serializeDatalake(Event event) {
        InvertedIndexEvent invertedIndexEvent = (InvertedIndexEvent) event;
        return invertedIndexEvent.ts().getTime()
                + "\t" + invertedIndexEvent.uuid()
                + "\t" + "INVERTED INDEX DATAMART"
                + "\t" + "DOCUMENT ADDED" + "\n";
    }
}
