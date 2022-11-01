package es.pentagono.invertedindex.tokenizers;
import es.pentagono.invertedindex.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GutenbergTokenizer implements Tokenizer {


    private static final List<String> stopwords = loadStopwords();

    @Override
    public List<String> tokenize(String content) {
        String s = content.replaceAll("[\\p{Punct}[0-9]+_\t\n\\x0B\f\n\r-]", " ");
        return Collections.list(new StringTokenizer(s, " ")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
    }
    public static List<String> loadStopwords() {
        try {
            return Files.readAllLines(Paths.get("C:/Users/juanc/IdeaProjects/BookQueryEngine/src/main/resources/stopwords.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean check(String word) {
        return stopwords.contains(word);
    }
}
