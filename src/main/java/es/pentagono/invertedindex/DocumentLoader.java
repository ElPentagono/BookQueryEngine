package es.pentagono.invertedindex;

import es.pentagono.Document;

import java.io.IOException;

public interface DocumentLoader {
    Document load(String documentId) throws IOException;
}
