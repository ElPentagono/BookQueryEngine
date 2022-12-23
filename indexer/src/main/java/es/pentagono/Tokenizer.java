package es.pentagono;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Tokenizer {

    Map<String, List<Integer>> tokenize(String content) throws IOException; //TODO change type
    boolean check(String word);
}
