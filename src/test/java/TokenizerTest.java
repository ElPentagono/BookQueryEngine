import es.pentagono.crawler.readers.GutenbergBookReader;
import es.pentagono.invertedindex.tokenizers.GutenbergTokenizer;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TokenizerTest {
    @Test
    public void name() {
        GutenbergTokenizer tokenizer = new GutenbergTokenizer();
        try {
            String content = new GutenbergBookReader().read("https://www.gutenberg.org/files/69270/69270-0.txt").content;
            List<String> cosa = tokenizer.tokenize(content);
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
