package es.pentagono;

import es.pentagono.events.DownloadEvent;

import java.io.IOException;

public interface BookReader {
    DownloadEvent read(String url) throws IOException;
    boolean exists(String url);
}
