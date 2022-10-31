package es.pentagono.crawler;

public interface DocumentPersister {
    void persist(String id, String metadata, String content);
    void persist(String event);
}
