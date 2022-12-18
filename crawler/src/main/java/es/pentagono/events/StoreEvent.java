package es.pentagono.events;

import es.pentagono.Event;

public class StoreEvent implements Event {

    public final long ts;
    public final String source;
    public final String uuid;
    public final String md5;

    public StoreEvent(long ts, String source, String uuid, String md5) {
        this.ts = ts;
        this.source = source;
        this.uuid = uuid;
        this.md5 = md5;
    }
}
