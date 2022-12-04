package es.pentagono;

public class Appearance {
    private transient final String uuid;
    public final String document;
    public final String word;
    public final String position;

    public Appearance(String uuid, String document, String word, String position) {
        this.uuid = uuid;
        this.document = document;
        this.word = word;
        this.position = position;
    }
}
