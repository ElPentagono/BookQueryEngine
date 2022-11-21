package es.pentagono.events;

import java.sql.Timestamp;

public class StoreEvent {

    public final Timestamp ts;
    public final String filename;

    public StoreEvent(String filename) {
        this.filename = filename;
        this.ts = new Timestamp(System.currentTimeMillis());
    }
}
