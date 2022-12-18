package es.pentagono.stores;

import es.pentagono.InvertedIndex;
import es.pentagono.InvertedIndexSerializer;
import es.pentagono.InvertedIndexStore;
import es.pentagono.InvertedIndexWriter;

public class FSInvertedIndexStore implements InvertedIndexStore {

    private final InvertedIndexWriter writer;
    private final InvertedIndexSerializer serializer;

    public FSInvertedIndexStore(InvertedIndexWriter writer, InvertedIndexSerializer serializer) {
        this.writer = writer;
        this.serializer = serializer;
    }

    @Override
    public void store(InvertedIndex index) {
        writer.write(serializer.serialize(index));
    }
}
