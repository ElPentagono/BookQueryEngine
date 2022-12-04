package es.pentagono;

public class Appearance {
    public final String uuid;
    public final String position;

    public Appearance(String[] values) {
        this.uuid = values[0];
        this.position = values[1];
    }

    public Appearance(String uuid, String position) {
        this.uuid = uuid;
        this.position = position;
    }
}
