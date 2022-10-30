package es.pentagono.crawler.serializers;

import es.pentagono.crawler.Event;
import es.pentagono.crawler.EventSerializer;

import java.lang.reflect.Field;

public class TsvEventSerializer implements EventSerializer {
    @Override
    public String serialize(Event event) {
        StringBuilder builder = new StringBuilder();
        for (Field field : event.getClass().getDeclaredFields()) {
            try {
                builder.append(field.get(event) + "\t");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return builder.toString();
    }
}
