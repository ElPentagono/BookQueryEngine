package es.pentagono.invertedindex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface InvertedIndexPersister {
    void persist(Map<String, String> invertedIndex);
}
