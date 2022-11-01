package es.pentagono.invertedindex.tokenizers;
import es.pentagono.invertedindex.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GutenbergTokenizer implements Tokenizer {


    private static final List<String> stopwords;

    static {
        try {
            stopwords = Files.readAllLines(Paths.get("stopwords/stopwords.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ArrayList<String> tokenize(String content) {
        return Stream.of(content.toLowerCase()
                        .replaceAll("[\\p{Punct}[0-9]+_\t\n\\x0B\f\n\r-]", "") // TODO quotation marks
                        .split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
    }

    public boolean check(String word) {
        return stopwords.contains(word);
    }
}
