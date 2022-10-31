package es.pentagono.crawler.readers;

import es.pentagono.crawler.BookReader;
import es.pentagono.crawler.DownloadEvent;
import es.pentagono.crawler.parsers.GutenbergBookParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.awt.BorderLayout.LINE_START;

public class GutenbergBookReader implements BookReader {

    private final GutenbergBookParser gutenbergBookParser;
    private static final int MAX_BOOK_SIZE = 10240000;

    public GutenbergBookReader() {
        this.gutenbergBookParser = new GutenbergBookParser();
    }

    @Override
    public DownloadEvent read(String url) throws IOException {
        Document document = Jsoup.connect(url).maxBodySize(MAX_BOOK_SIZE).get();
        return gutenbergBookParser.parse(url, getBookKeepingBreaklines(document));
    }

    private String getBookKeepingBreaklines(Document document) {
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));
        return Jsoup.clean(
            document.html().replaceAll("\\\\n", "\n"),
            "",
            Whitelist.none(),
            new Document.OutputSettings().prettyPrint(false)
        );
    }
}
