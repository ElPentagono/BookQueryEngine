package es.pentagono;

import java.util.Map;

public interface InvertedIndexPersister {
    void persistConfig(Map<String, String> invertedIndex);
    void persistConfig(String event);
    void persistDatalake(String event);
}
