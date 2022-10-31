package es.pentagono.crawler;

import java.io.IOException;

public abstract class Source {

    protected String url;
    protected BookReader bookReader;
    protected boolean nextBookExists;

    public DownloadEvent readBook() throws IOException {
        return bookReader.read(url);
    }
}