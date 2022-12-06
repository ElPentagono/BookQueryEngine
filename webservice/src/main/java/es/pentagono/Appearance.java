package es.pentagono;

public class Appearance {
    private transient final String uuid;
    public transient final Metadata metadata;
    public final String document;
    public final String word;
    public final String position;

    public Appearance(String uuid, Metadata metadata, String word, String position) {
        this.uuid = uuid;
        this.metadata = metadata;
        this.document = metadata.title;
        this.word = word;
        this.position = position;
    }
}
