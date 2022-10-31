package es.pentagono.crawler;

import es.pentagono.Serializer;

public interface EventSerializer extends Serializer {
    String serialize(Event event);
}
