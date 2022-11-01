package es.pentagono.invertedindex.builders;

import es.pentagono.Document;
import es.pentagono.InvertedIndex;
import es.pentagono.invertedindex.Tokenizer;
import es.pentagono.invertedindex.tokenizers.GutenbergTokenizer;

import java.io.IOException;
import java.util.*;

public class InvertedIndexBuilder {

    public final Tokenizer tokenize;

    public InvertedIndexBuilder(Tokenizer tokenizer) {
        this.tokenize = tokenizer;
    }

    public InvertedIndex build(Document document) {
        HashMap<String, List<String[]>> invertedIndex = new HashMap<>();
        processDocument(invertedIndex, document);
        return new InvertedIndex(invertedIndex);
    }

    private void processDocument(HashMap<String, List<String[]>> invertedIndex, Document document) {
        try {
            List<String> content = this.tokenize.tokenize(document.content);
            for (int i = 0; i < content.size(); i++) {
                if (content.get(i).equals("")) continue;
                processWord(invertedIndex, document, content, i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processWord(HashMap<String, List<String[]>> invertedIndex, Document document, List<String> content, int i) {
        if (this.tokenize.check(content.get(i).toLowerCase())) return;
        addOccurrence(content.get(i).toLowerCase(), new String[] {document.id, String.valueOf(i) }, invertedIndex);
    }

    private void addOccurrence(String word, String[] occurrence, HashMap<String, List<String[]>> invertedIndex ) {
        List<String[]> ocurrences = invertedIndex.get(word);
        if (ocurrences == null) {
            createListOfOccurrences(word, occurrence, invertedIndex);
            return;
        }
        ocurrences.add(occurrence);
    }

    private static void createListOfOccurrences(String word, String[] ocurrence, HashMap<String, List<String[]>> invertedIndex) {
        ArrayList<String[]> list = new ArrayList<>();
        list.add(ocurrence);
        invertedIndex.put(word, list);
    }
}
