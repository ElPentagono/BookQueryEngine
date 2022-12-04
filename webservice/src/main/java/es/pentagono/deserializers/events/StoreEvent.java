package es.pentagono.deserializers.events;

import es.pentagono.Event;

public class StoreEvent implements Event {

    public final String ts;
    public final String source;
    public transient final String uuid;
    public transient final String md5;

    public StoreEvent(String ts, String source, String uuid, String md5) {
        this.ts = ts;
        this.source = source;
        this.uuid = uuid;
        this.md5 = md5;
    }
}
