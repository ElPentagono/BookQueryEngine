package es.pentagono;

public interface MetadataPersister {
    void persist(Document document);
    void persist(Event event);
}
