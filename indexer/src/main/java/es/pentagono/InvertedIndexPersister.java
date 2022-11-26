package es.pentagono;

import java.util.Map;

public interface InvertedIndexPersister {
    void persist(Map<String, String> invertedIndex);
    void persist(String event);
}
