package es.pentagono.crawler;

import java.io.IOException;
import java.net.URL;

public interface BookReader {
    DownloadEvent read(String url) throws IOException;
}
