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
                return unavaibleBooksCounter < MAX_CALLS_WITHOUT_BOOKS;
            }

            @Override
            public Event next() {
                try {
                    currentBookId++;
                    DownloadEvent event = reader.read(constructURL());
                    if (event.content.equals("")) {
                        unavaibleBooksCounter++;
                        if (!hasNext()) return null;
                        return next();
                    }
                    return event;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private String constructURL() {
                return URL + "/" + currentBookId + "/pg" + currentBookId + ".txt";
            }
        };
    }
}
