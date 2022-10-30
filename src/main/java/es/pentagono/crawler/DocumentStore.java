package es.pentagono.crawler;

import es.pentagono.Document;

public interface DocumentStore {
    String store(Document document);
}
