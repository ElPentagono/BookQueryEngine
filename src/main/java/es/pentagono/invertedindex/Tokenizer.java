package es.pentagono.invertedindex;

import java.io.IOException;
import java.util.List;

public interface Tokenizer {
    List<String> tokenize(String content) throws IOException; //TODO
}
