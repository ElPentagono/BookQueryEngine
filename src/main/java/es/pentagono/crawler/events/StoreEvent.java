package es.pentagono.crawler.events;

import es.pentagono.crawler.Event;

import java.sql.Timestamp;

public class StoreEvent implements Event {

    public final Timestamp ts;
    public final String source;
    public final String uuid;
    public final String md5;

    public StoreEvent(Timestamp ts, String source, String uuid, String md5) {
        this.ts = ts;
        this.source = source;
        this.uuid = uuid;
        this.md5 = md5;
    }
}
