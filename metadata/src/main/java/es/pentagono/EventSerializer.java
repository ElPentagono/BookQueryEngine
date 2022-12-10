package es.pentagono;

public interface EventSerializer {
    String serializeConfig(Event event);
    String serializeDatalake(Event event);
}
