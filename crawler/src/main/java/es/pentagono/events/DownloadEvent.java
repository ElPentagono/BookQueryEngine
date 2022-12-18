package es.pentagono.events;

import es.pentagono.Event;
import es.pentagono.Metadata;

import java.sql.Timestamp;
import java.util.Map;

public class DownloadEvent implements Event {
    public final String source;
    public final Metadata metadata;
    public final String content;
    public final Timestamp ts;

    public DownloadEvent(String source, Metadata metadata, String content, Timestamp ts) {
        this.source = source;
        this.metadata = metadata;
        this.content = content;
        this.ts = ts;
    }
}
