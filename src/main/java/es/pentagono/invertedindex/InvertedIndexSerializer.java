package es.pentagono.invertedindex;

import es.pentagono.InvertedIndex;

public interface InvertedIndexSerializer {
    String serialize(InvertedIndex index);
}
