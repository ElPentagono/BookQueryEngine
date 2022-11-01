import es.pentagono.crawler.BookReader;
import es.pentagono.crawler.events.DownloadEvent;
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
        DownloadEvent result = bookReader.read("https://www.gutenberg.org/cache/epub/10/pg10.txt");
        assertThat(result.source).isEqualTo("https://www.gutenberg.org/cache/epub/10/pg10.txt");
    }

    @Test
    public void testExists() {
        assertThat(bookReader.exists("https://www.gutenberg.org/cache/epub/5002/pg5002.txt")).isTrue();
        assertThat(bookReader.exists("https://www.gutenberg.org/cache/epub/5001/pg5001.txt")).isFalse();
    }

    @Parameterized.Parameters
    public static Collection<BookReader> implementations() {
        return List.of(
            new GutenbergBookReader()
        );
    }
}
