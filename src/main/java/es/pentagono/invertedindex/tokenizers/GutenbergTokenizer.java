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
        return Collections.list(new StringTokenizer(content, " ")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
    }

    public static List<String> loadStopwords() {
        try {
            return Files.readAllLines(
                    Paths.get(
                            Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                                    .getResource("stopwords.txt")).getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean check(String word) {
        return stopwords.contains(word);
    }
}
