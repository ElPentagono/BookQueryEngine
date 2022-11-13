package es.pentagono;

import java.util.Map;

public interface InvertedIndexSerializer {
    Map<String, String> serialize(InvertedIndex index);
}
