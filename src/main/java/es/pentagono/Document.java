package es.pentagono;

public class Document {
    public int uuid;
    public Metadata metadata;
    public String content;

    public Document(int uuid, Metadata metadata, String content) {
        this.uuid = uuid;
        this.metadata = metadata;
        this.content = content;
    }
}
