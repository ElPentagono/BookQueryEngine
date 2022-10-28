package es.pentagono.crawler;

import es.pentagono.Metadata;

public class Item {
    public String source;
    public String metadata;
    public String content;

    public Item(String source, String metadata, String content) {
        this.source = source;
        this.metadata = metadata;
        this.content = content;
    }
}
