package es.pentagono.crawler.persisters;

import es.pentagono.crawler.Event;
import es.pentagono.crawler.EventPersister;

import java.lang.reflect.Field;

public class TsvEventPersister implements EventPersister {
    @Override
    public void persist(Event event) {
        //TODO check if exits and if not create else append line
    }
}
