package es.pentagono.crawler.sources;

import es.pentagono.crawler.events.DownloadEvent;
import es.pentagono.crawler.Source;
import es.pentagono.crawler.readers.GutenbergBookReader;

import java.io.IOException;

public class GutenbergSource extends Source {

    public GutenbergSource() {
        this.url = "https://www.gutenberg.org/cache/epub";
        this.bookReader = new GutenbergBookReader();
    }

    @Override
    public DownloadEvent readBook(int id) throws IOException {
        this.url = this.url + "/" + id + "/pg" + id + ".txt";
        return super.readBook(id);
    }
}
