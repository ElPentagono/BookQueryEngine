package es.pentagono.invertedindex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Tokenizer {
    ArrayList<String> tokenize(String content) throws IOException; //TODO change type
}
