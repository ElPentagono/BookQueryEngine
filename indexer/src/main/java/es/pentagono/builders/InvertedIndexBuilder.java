package es.pentagono.builders;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import es.pentagono.Document;
import es.pentagono.InvertedIndex;
import es.pentagono.Tokenizer;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InvertedIndexBuilder {

    public final Tokenizer tokenizer;

    public InvertedIndexBuilder(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public InvertedIndex build(Document document) {
        Multimap<String, String[]> invertedIndex = ArrayListMultimap.create();
        processDocument(invertedIndex, document);
        return new InvertedIndex(invertedIndex);
    }

    private void processDocument(Multimap<String, String[]> invertedIndex, Document document) {
        try {
            Map<String, List<Integer>> occurrences = (Map<String, List<Integer>>) this.tokenizer.tokenize(document.content);
            for (Map.Entry<String, List<Integer>> occurrence : occurrences.entrySet())
                addWordOccurence(invertedIndex, document, occurrence);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addWordOccurence(Multimap<String, String[]> invertedIndex, Document document, Map.Entry<String, List<Integer>> occurrence) {
        List<String[]> wordOccurences = addDocumentId(document, occurrence);
        invertedIndex.putAll(occurrence.getKey(), wordOccurences);
    }

    private static List<String[]> addDocumentId(Document document, Map.Entry<String, List<Integer>> occurrence) {
        return occurrence.getValue().stream()
            .map(position -> new String[]{document.id, String.valueOf(position)})
            .collect(Collectors.toList());
    }
}
