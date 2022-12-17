package es.pentagono;

public interface DocumentStore extends Store {
    String store(Document document);
}
