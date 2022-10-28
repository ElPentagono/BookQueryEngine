package es.pentagono.invertedindex;

import java.util.List;

public interface Tokenizer {
    List<String> tokenize(String content);
}
