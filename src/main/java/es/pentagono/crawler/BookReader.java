package es.pentagono.crawler;

import java.io.IOException;

public interface BookReader {
    Item read(int id) throws IOException;
}
