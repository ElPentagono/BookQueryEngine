package es.pentagono.crawler;

import java.io.IOException;

public abstract class Source {

    protected String url;
    protected BookReader bookReader;

    public Event readBook(int id) throws IOException {
        return bookReader.read(url);
    }
}