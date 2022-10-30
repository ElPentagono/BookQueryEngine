package es.pentagono.crawler;

import es.pentagono.crawler.readers.GutenbergBookReader;

import java.io.IOException;
import java.net.MalformedURLException;

public class GutenbergSource extends Source {

    public GutenbergSource() {
        this.url = "https://www.gutenberg.org/cache/epub";
        this.bookReader = new GutenbergBookReader();
    }

    @Override
    public Event readBook(int id) throws IOException {
        this.url = this.url + "/" + id + "/pg" + id + ".txt";
        return super.readBook(id);
    }
}
