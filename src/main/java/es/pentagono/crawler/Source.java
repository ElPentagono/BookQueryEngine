package es.pentagono.crawler;

import java.util.Iterator;

public interface Source {
    Iterator<Event> all();
}