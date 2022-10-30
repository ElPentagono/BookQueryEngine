package es.pentagono.crawler.events;

import es.pentagono.crawler.Event;

import java.sql.Timestamp;
import java.util.Map;

public class DownloadEvent implements Event {
    public final String source;
    public final Map<String, String> metadata;
    public final String content;
    public final Timestamp ts;

    public DownloadEvent(String source, Map<String, String> metadata, String content, Timestamp ts) {
        this.source = source;
        this.metadata = metadata;
        this.content = content;
        this.ts = ts;
    }
}
