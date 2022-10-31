package es.pentagono.crawler.sources;

import es.pentagono.crawler.BookReader;
import es.pentagono.crawler.DownloadEvent;
import es.pentagono.crawler.Event;
import es.pentagono.crawler.Source;
import es.pentagono.crawler.readers.GutenbergBookReader;

import java.io.IOException;
import java.util.Iterator;

public class GutenbergSource implements Source {

    private static int unavaibleBooksCounter = 0;
    private static int currentBookId = 0;
    private static final int MAX_CALLS_WITHOUT_BOOKS = 20;
    private static final String URL = "https://www.gutenberg.org/cache/epub";



    private final BookReader reader = new GutenbergBookReader();

    @Override
    public Iterator<Event> all() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                for (int i = 0; i < MAX_CALLS_WITHOUT_BOOKS; i++) {
                    if (reader.exists(constructURL(++currentBookId))) return true;
                    currentBookId++;
                }
                return false;
            }

            @Override
            public Event next() {
                try {
                    return reader.read(constructURL(currentBookId++));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private String constructURL(int id) {
                return URL + "/" + id + "/pg" + id + ".txt";
            }
        };
    }
}
