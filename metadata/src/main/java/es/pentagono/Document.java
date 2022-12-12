package es.pentagono;

public class Document {
    public final String uuid;
    public final String metadata;

    public Document(String uuid, String metadata) {
        this.uuid = uuid;
        this.metadata = metadata;
    }
}
