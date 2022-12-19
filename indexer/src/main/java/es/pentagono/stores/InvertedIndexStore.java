package es.pentagono.stores;

import es.pentagono.InvertedIndex;
import es.pentagono.InvertedIndexSerializer;
import es.pentagono.InvertedIndexWriter;
import es.pentagono.Store;

public interface InvertedIndexStore extends Store {
    void store(InvertedIndex index);
    static InvertedIndexStore create(InvertedIndexWriter writer, InvertedIndexSerializer serializer) {
        return index -> writer.write(serializer.serialize(index));
    }
}
