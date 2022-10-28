package es.pentagono.crawler;

import es.pentagono.Document;

public interface DocumentLoader {
    Document load(int fileId);
}
