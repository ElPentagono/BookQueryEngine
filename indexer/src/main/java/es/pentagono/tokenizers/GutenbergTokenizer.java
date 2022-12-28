package es.pentagono.tokenizers;

import es.pentagono.Tokenizer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class GutenbergTokenizer implements Tokenizer {

    private static final List<String> stopwords = loadStopwords();
    private Map<String, List<Integer>> tokenizeContent;

    @Override
    public Map<String, List<Integer>> tokenize(String content) {
        tokenizeContent = new HashMap<>();
        String cleanContent = content.replaceAll("[\\p{Punct}[0-9]+_\\-—\t\n\\x0B\f\n\r‘’“”]", " ");
        return processDocument(Collections.list(new StringTokenizer(cleanContent, " ")).stream()
                .map(token -> (String) token)
                .filter(word -> word.length() >= 2)
                .collect(Collectors.toList()));
    }

    private static List<String> loadStopwords() {
        try {
            return new BufferedReader(new InputStreamReader(GutenbergTokenizer.class.getClassLoader().getResourceAsStream("stopwords.txt")))
                    .lines()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<Integer>> processDocument(List<String> content) {
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).equals("")) continue;
            processOccurrence(content, i);
        }
        return tokenizeContent;
    }

    private void processOccurrence(List<String> content, int pos) {
        if (check(content.get(pos).toLowerCase())) return;
        addOccurrence(content.get(pos).toLowerCase(), pos);
    }

    private void addOccurrence(String word, int occurrence) {
        List<Integer> occurrences = tokenizeContent.get(word);
        if (occurrences == null) {
            createListOfOccurrences(word, occurrence);
            return;
        }
        occurrences.add(occurrence);
    }

    private void createListOfOccurrences(String word, int occurrence) {
        List<Integer> list = new ArrayList<>();
        list.add(occurrence);
        tokenizeContent.put(word, list);
    }

    public boolean check(String word) {
        return stopwords.contains(word);
    }
}
