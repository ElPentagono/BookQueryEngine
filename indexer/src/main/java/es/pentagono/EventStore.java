package es.pentagono;

public interface EventStore extends Store {
    void store(Event event);
}
