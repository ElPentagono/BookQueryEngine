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

    private List<String> stopwords;

    public void loadStopwords() throws IOException {
        this.stopwords =  Files.readAllLines(Paths.get("stopwords/stopwords.txt"));
    }

    @Override
    public ArrayList<String> tokenize(String content) {
        return Stream.of(content.toLowerCase()
                        .replaceAll("[\t\n\\x0B\f\r\\p{Punct}[0-9]+]", "") // TODO quotation marks
                        .split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
    }

    public boolean check(String word) throws IOException {
        loadStopwords();
        return this.stopwords.contains(word);
    }
}
