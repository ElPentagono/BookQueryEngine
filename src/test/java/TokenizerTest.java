import es.pentagono.crawler.readers.GutenbergBookReader;
import es.pentagono.invertedindex.tokenizers.GutenbergTokenizer;
import org.junit.Test;

import java.io.IOException;

public class TokenizerTest {
    @Test
    public void name() {
        GutenbergTokenizer tokenizer = new GutenbergTokenizer();
        try {
            System.out.println(tokenizer.tokenize(new GutenbergBookReader().read("https://www.gutenberg.org/files/69270/69270-0.txt").content));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
