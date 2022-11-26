package es.pentagono;

public interface MetadataPersister {
    void persist(String filename, String content);
    void persist(Event event);
}
