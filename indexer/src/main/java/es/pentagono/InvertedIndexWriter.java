package es.pentagono;

import java.util.Map;

public interface InvertedIndexWriter extends Writer {
    void write(Map<String, String> index);
}
