package es.pentagono;

import java.sql.Timestamp;

public class InvertedIndexEvent implements Event{
    private final Timestamp ts;
    private final String uuid;

    public InvertedIndexEvent(Timestamp ts, String uuid) {
        this.ts = ts;
        this.uuid = uuid;
    }

    public Timestamp ts() {
        return ts;
    }

    public String uuid() {
        return uuid;
    }
}
