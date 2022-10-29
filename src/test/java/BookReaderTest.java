import es.pentagono.crawler.BookReader;
import es.pentagono.crawler.Item;
import es.pentagono.crawler.readers.GutenbergBookReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class BookReaderTest {

    private final BookReader bookReader;

    public BookReaderTest(BookReader bookReader) {
        this.bookReader = bookReader;
    }

    @Test
    public void testRead() throws IOException {
        Item result = bookReader.read(10);
        assertThat(result.source).isEqualTo("https://www.gutenberg.org/cache/epub/10/pg10.txt");
    }

    @Parameterized.Parameters
    public static Collection<BookReader> implementations() {
        return List.of(
            new GutenbergBookReader()
        );
    }
}