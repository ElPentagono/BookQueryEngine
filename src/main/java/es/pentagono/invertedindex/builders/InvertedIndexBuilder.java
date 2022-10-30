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
        _process_document(invertedIndex, content);
        return new InvertedIndex(invertedIndex);
    }

    public void _process_document(HashMap<String, List<int[]>> invertedIndex, Document document) throws IOException {
        ArrayList<String> content = this.tokenize.tokenize(document.content);
        for (int i = 0; i<content.size() ; i++) {
            if (this.tokenize.check(content.get(i))) continue;
            int[] ocurrence = new int[] {document.uuid, i};
            addOcurrence(content.get(i), ocurrence, invertedIndex);
        }
    }

    private void addOcurrence(String word, int[] ocurrence, HashMap<String, List<int[]>> invertedIndex ) {
        List<int[]> ocurrences = invertedIndex.get(word);
        if(ocurrences == null){
            invertedIndex.put(word, new ArrayList<>(List.of(ocurrence)));
            return;
        }
        ocurrences.add(ocurrence);
    }
}
