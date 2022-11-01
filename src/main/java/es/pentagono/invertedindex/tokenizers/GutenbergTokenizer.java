package es.pentagono.invertedindex.tokenizers;

import es.pentagono.invertedindex.Tokenizer;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

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
            URL stopwords = GutenbergTokenizer.class.getClassLoader().getResource("stopwords.txt");
            return Files.readAllLines(Path.of(stopwords.toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean check(String word) {
        return stopwords.contains(word);
    }
}
