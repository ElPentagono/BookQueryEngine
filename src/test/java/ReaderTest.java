import es.pentagono.crawler.readers.GutenbergBookReader;
import org.junit.Test;

public class ReaderTest {

    @Test
    public void name() {
        GutenbergBookReader reader = new GutenbergBookReader();
        System.out.println(reader.exists("https://www.gutenberg.org/cache/epub/5002/pg5002.txt"));
        System.out.println(reader.exists("https://www.gutenberg.org/cache/epub/5001/pg5001.txt"));
    }
}
