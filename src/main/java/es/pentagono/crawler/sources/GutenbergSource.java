package es.pentagono.crawler.sources;

import es.pentagono.crawler.DownloadEvent;
import es.pentagono.crawler.Source;
import es.pentagono.crawler.readers.GutenbergBookReader;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class GutenbergSource extends Source {

    private static int unavaibleBooksCounter = 0;
    private static int currentBookId = 0;
    private static final int MAX_CALLS_WITHOUT_BOOKS = 20;

    public GutenbergSource(GutenbergBookReader gutenbergBookReader) {
        super(gutenbergBookReader);
        this.url = "https://www.gutenberg.org/cache/epub";
    }

    public DownloadEvent readBook() throws IOException {
        DownloadEvent downloadEvent = super.readBook();
        if (bookNotFounded(downloadEvent)) return null;
        unavaibleBooksCounter=0;
        return downloadEvent;
    }

    public Iterator<String> all() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return unavaibleBooksCounter < MAX_CALLS_WITHOUT_BOOKS;
            }

            @Override
            public String next() {
                currentBookId++;
                try {
                    if (Objects.equals(readBook().content, "")) unavaibleBooksCounter++;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return url + "/" + currentBookId + "/pg" + currentBookId + ".txt";
            }
        };
    }

    private boolean bookNotFounded(DownloadEvent downloadEvent) {
        if (downloadEvent == null) {
            unavaibleBooksCounter++;
            return true;
        }
        return false;
    }
}
