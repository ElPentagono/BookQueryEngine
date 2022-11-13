package es.pentagono;

import java.util.Iterator;

public interface Source {
    Iterator<Event> all();
}