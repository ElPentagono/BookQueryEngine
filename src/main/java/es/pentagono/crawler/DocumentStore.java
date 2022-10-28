package es.pentagono.crawler;

import es.pentagono.Document;

public interface DocumentStore {
    void store(int id, Document document);
}
