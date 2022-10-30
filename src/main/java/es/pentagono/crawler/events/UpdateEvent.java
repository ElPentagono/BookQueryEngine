package es.pentagono.crawler.events;

import es.pentagono.crawler.Event;

import java.sql.Timestamp;

public class UpdateEvent implements Event {

    public final Timestamp ts;

    public UpdateEvent(Timestamp ts) {
        this.ts = ts;
    }

    @Override
    public Timestamp ts() {
        return this.ts;
    }
}
