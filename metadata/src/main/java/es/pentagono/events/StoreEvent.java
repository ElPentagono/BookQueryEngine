package es.pentagono.events;

import es.pentagono.Event;

public class StoreEvent implements Event {

    public final long ts;
    public final String filename;

    public StoreEvent(long ts, String filename) {
        this.ts = ts;
        this.filename = filename;
    }
}
