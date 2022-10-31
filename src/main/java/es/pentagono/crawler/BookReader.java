package es.pentagono.crawler;

import java.io.IOException;

public interface BookReader {
    DownloadEvent read(String url) throws IOException; // TODO check what happens with empty books
}
