package es.pentagono;

public interface EventSerializer extends Serializer {
    String serializeConfig(Event event);
    String serializeDatalake(Event event);
}
