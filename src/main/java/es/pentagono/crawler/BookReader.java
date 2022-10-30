package es.pentagono.crawler;

import es.pentagono.crawler.events.DownloadEvent;

import java.io.IOException;

public interface BookReader {
    DownloadEvent read(String url) throws IOException;
    boolean exists(String url);
}
