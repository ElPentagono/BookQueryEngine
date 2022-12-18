package es.pentagono.events;

import es.pentagono.Event;
import es.pentagono.Metadata;

public class DownloadEvent implements Event {
    public final String source;
    public final Metadata metadata;
    public final String content;
    public final long ts;

    public DownloadEvent(String source, Metadata metadata, String content, long ts) {
        this.source = source;
        this.metadata = metadata;
        this.content = content;
        this.ts = ts;
    }
}
