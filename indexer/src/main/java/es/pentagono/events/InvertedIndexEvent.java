package es.pentagono.events;

import es.pentagono.Event;

public class InvertedIndexEvent implements Event {
    public final long ts;
    public final String uuid;

    public InvertedIndexEvent(long ts, String uuid) {
        this.ts = ts;
        this.uuid = uuid;
    }
}
