package es.pentagono;

public interface InvertedIndexStore {
    void store(InvertedIndex index);
    void store(Event event);
}
