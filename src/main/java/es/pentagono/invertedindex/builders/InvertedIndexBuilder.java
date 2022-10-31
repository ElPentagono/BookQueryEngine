package es.pentagono.invertedindex.builders;

import es.pentagono.Document;
import es.pentagono.InvertedIndex;
import es.pentagono.invertedindex.tokenizers.GutenbergTokenizer;

import java.io.IOException;
import java.util.*;

public class InvertedIndexBuilder {

    public final GutenbergTokenizer tokenize;

    public InvertedIndexBuilder(GutenbergTokenizer tokenize) {
        this.tokenize = tokenize;
    }

    public InvertedIndex build(Document content) throws IOException {
        HashMap<String, List<int[]>> invertedIndex = new HashMap<>();
        processDocument(invertedIndex, content);
        return new InvertedIndex(invertedIndex);
    }

    private void processDocument(HashMap<String, List<int[]>> invertedIndex, Document document) throws IOException {
        ArrayList<String> content = this.tokenize.tokenize(document.content);
        for (int i = 0; i < content.size() ; i++)
            processWord(invertedIndex, document, content, i);
    }

    private void processWord(HashMap<String, List<int[]>> invertedIndex, Document document, ArrayList<String> content, int i) throws IOException {
        if (this.tokenize.check(content.get(i))) return;
        addOcurrence(content.get(i), new int[] {document.uuid, i}, invertedIndex);
    }

    private void addOcurrence(String word, int[] ocurrence, HashMap<String, List<int[]>> invertedIndex ) {
        List<int[]> ocurrences = invertedIndex.get(word);
        if (ocurrences == null) {
            invertedIndex.put(word, new ArrayList<>(List.of(ocurrence)));
            return;
        }
        ocurrences.add(ocurrence);
    }
}
