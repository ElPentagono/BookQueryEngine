package es.pentagono.sources;

import es.pentagono.BookReader;
import es.pentagono.Configuration;
import es.pentagono.Source;
import es.pentagono.Event;
import es.pentagono.readers.GutenbergBookReader;

import java.io.IOException;
import java.util.Iterator;

public class GutenbergSource implements Source {

    private static int currentBookId = Integer.parseInt(Configuration.getProperty("currentBookId"));
    private static final int MAX_FAILED_CALLS = Integer.parseInt(Configuration.getProperty("maxFailedCalls"));
    private static final String URL = "https://www.gutenberg.org/cache/epub";
    private final BookReader reader = new GutenbergBookReader();

    @Override
    public Iterator<Event> all() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                for (int i = 0; i < MAX_FAILED_CALLS; i++) {
                    if (reader.exists(constructURL(1 + currentBookId))) return true;
                    updateCurrentBookId();
                }
                return false;
            }

            @Override
            public Event next() {
                try {
                    updateCurrentBookId();
                    return reader.read(constructURL(currentBookId));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private String constructURL(int id) {
                return URL + "/" + id + "/pg" + id + ".txt";
            }
        };
    }

    private static void updateCurrentBookId() {
        Configuration.setProperty("currentBookId", String.valueOf(++currentBookId));
        Configuration.saveConfig();
    }
}
