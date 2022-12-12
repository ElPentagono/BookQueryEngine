package es.pentagono;

public interface DocumentPersister {
    void persist(String id, String metadata, String content);
    void persistConfig(String event);
    void persistEvent(String event);
}
