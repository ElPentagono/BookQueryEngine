import es.pentagono.Document;
import es.pentagono.Tokenizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvertedIndexBuilderMap {

    public final Tokenizer tokenizer;

    public InvertedIndexBuilderMap(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public InvertedIndexMap build(Document document) {
        HashMap<String, List<String[]>> invertedIndex = new HashMap<>();
        processDocument(invertedIndex, document);
        return new InvertedIndexMap(invertedIndex);
    }

    private void processDocument(HashMap<String, List<String[]>> invertedIndex, Document document) {
        try {
            Map<String, List<Integer>> occurrences = (Map<String, List<Integer>>) this.tokenizer.tokenize(document.content);
            for (Map.Entry<String, List<Integer>> occurrence : occurrences.entrySet())
                addWordOccurence(invertedIndex, document, occurrence);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addWordOccurence(Map<String, List<String[]>> invertedIndex, Document document, Map.Entry<String, List<Integer>> occurrence) {
        List<String[]> wordOccurences = addDocumentId(document, occurrence);
        invertedIndex.put(occurrence.getKey(), wordOccurences);
    }

    private static List<String[]> addDocumentId(Document document, Map.Entry<String, List<Integer>> occurrence) {
        return occurrence.getValue().stream()
            .map(position -> new String[]{document.id, String.valueOf(position)})
            .collect(Collectors.toList());
    }
}
