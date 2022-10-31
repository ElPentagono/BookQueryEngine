package es.pentagono.crawler.sources;

import es.pentagono.crawler.DownloadEvent;
import es.pentagono.crawler.Source;
import es.pentagono.crawler.readers.GutenbergBookReader;

import java.io.IOException;
import java.util.Objects;

public class GutenbergSource extends Source {

    private static int booksUnavaibleInARow = 0;

    public GutenbergSource() {
        this.url = "https://www.gutenberg.org/cache/epub";
        this.bookReader = new GutenbergBookReader();
    }


    public DownloadEvent readBook() throws IOException {

        //this.url = this.url + "/" + id + "/pg" + id + ".txt";
        DownloadEvent downloadEvent = super.readBook();
        if (bookNotFounded(downloadEvent)) return null;
        return downloadEvent;
    }



    private boolean bookNotFounded(DownloadEvent downloadEvent) {
        if (downloadEvent == null) {
            booksUnavaibleInARow++;
            return true;
        }
        return false;
    }

    public boolean hasNext() {
        return this.nextBookExists;
    }
}
