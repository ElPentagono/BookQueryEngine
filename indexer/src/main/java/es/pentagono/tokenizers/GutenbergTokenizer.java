package es.pentagono.tokenizers;

import es.pentagono.Document;
import es.pentagono.Tokenizer;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class GutenbergTokenizer implements Tokenizer {

    private static final List<String> stopwords = loadStopwords();
    private final Map<String, List<Integer>> tokenizeContent = new HashMap<>();

    @Override
    public Map<String, List<Integer>> tokenize(Document document) {
        String s = document.content.replaceAll("[\\p{Punct}[0-9]+_\t\n\\x0B\f\n\r-]", " ");
        return processDocument(Collections.list(new StringTokenizer(s, " ")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList()));
    }
    public static List<String> loadStopwords() {
        try {
            URL stopwords = GutenbergTokenizer.class.getClassLoader().getResource("stopwords.txt");
            return Files.readAllLines(Path.of(stopwords.toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<Integer>> processDocument(List<String> content) {
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).equals("")) continue;
            processWord(content, i);
        }
        return tokenizeContent;
    }

    private void processWord(List<String> content, int i) {
        if (check(content.get(i).toLowerCase())) return;
        addOccurrence(content.get(i).toLowerCase(), i);
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
        ArrayList<Integer> list = new ArrayList<>();
        list.add(occurrence);
        tokenizeContent.put(word, list);
    }

    public boolean check(String word) {
        return stopwords.contains(word);
    }
}
