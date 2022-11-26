package es.pentagono.serializers;

import es.pentagono.Event;
import es.pentagono.EventSerializer;
import es.pentagono.InvertedIndexEvent;

public class TsvEventSerializer implements EventSerializer {
    @Override
    public String serialize(Event event) {
        InvertedIndexEvent invertedIndexEvent = (InvertedIndexEvent) event;
        return invertedIndexEvent.Ts().getNanos() + "\t" + invertedIndexEvent.Uuid() + "\n";
    }
}
