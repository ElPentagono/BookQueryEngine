package es.pentagono;

public class Appearance {
    public final String uuid;
    public final String position;

    public Appearance(String[] values) {
        this.uuid = values[0];
        this.position = values[1];
    }
}
