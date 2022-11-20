package es.pentagono;

public interface EventSerializer extends Serializer {
    String serialize(Event event);
}
