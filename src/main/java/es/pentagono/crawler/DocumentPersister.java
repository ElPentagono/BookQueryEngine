package es.pentagono.crawler;

public interface DocumentPersister {
    void persist(int id, String metadata, String content);
}
