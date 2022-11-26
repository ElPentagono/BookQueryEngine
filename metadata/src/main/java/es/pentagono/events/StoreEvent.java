package es.pentagono.events;

import es.pentagono.Event;

import java.sql.Timestamp;

public class StoreEvent implements Event {

    public final Timestamp ts;
    public final String filename;

    public StoreEvent(String filename) {
        this.filename = filename;
        this.ts = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "\n" + filename + "\t" + ts.getTime();
    }
}
