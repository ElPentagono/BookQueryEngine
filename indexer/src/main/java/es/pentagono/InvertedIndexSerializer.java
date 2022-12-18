package es.pentagono;

import java.util.Map;

public interface InvertedIndexSerializer extends Serializer {
    Map<String, String> serialize(InvertedIndex index);
}
