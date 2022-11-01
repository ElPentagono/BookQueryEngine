package es.pentagono.invertedindex;

import es.pentagono.InvertedIndex;

import java.util.Map;

public interface InvertedIndexSerializer {
    Map<String, String> serialize(InvertedIndex index);
}
