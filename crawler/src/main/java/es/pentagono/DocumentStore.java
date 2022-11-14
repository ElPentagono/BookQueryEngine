package es.pentagono;

public interface DocumentStore {
    String store(Document document);
    void store(Event event);
}
