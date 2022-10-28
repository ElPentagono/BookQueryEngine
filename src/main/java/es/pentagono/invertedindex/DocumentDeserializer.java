package es.pentagono.invertedindex;

import es.pentagono.Document;

public interface DocumentDeserializer {
    Document deserialize(int documentId);
}
