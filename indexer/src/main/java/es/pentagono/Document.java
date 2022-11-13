package es.pentagono;

public class Document {
    public final String id;
    public final Metadata metadata;
    public final String content;

    public Document(String id, Metadata metadata, String content) {
        this.id = id;
        this.metadata = metadata;
        this.content = content;
    }
}
