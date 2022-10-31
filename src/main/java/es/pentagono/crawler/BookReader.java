package es.pentagono.crawler;

import java.io.IOException;

public interface BookReader {
    DownloadEvent read(String url) throws IOException;
    boolean exists(String url);
}
