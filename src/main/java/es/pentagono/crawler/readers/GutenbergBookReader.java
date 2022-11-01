package es.pentagono.crawler.readers;

import es.pentagono.crawler.BookReader;
import es.pentagono.crawler.events.DownloadEvent;
import es.pentagono.crawler.EventParser;
import es.pentagono.crawler.parsers.GutenbergDownloadEventParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class GutenbergBookReader implements BookReader {
    private static final EventParser DownloadEventParser = new GutenbergDownloadEventParser();
    private static final int MAX_BOOK_SIZE = 10240000;


    @Override
    public DownloadEvent read(String url) throws IOException {
        Document document = Jsoup.connect(url).maxBodySize(MAX_BOOK_SIZE).get();
        return (DownloadEvent) DownloadEventParser.parse(url, getBookKeepingBreakLines(document));
    }

    @Override
    public boolean exists(String url) {
        try {
            HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
            int responseCode = huc.getResponseCode();
            return HttpURLConnection.HTTP_OK == responseCode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBookKeepingBreakLines(Document document) {
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));
        return Jsoup.clean(
                document.html().replaceAll("\\\\n", "\n"),
                "",
                Whitelist.none(),
                new Document.OutputSettings().prettyPrint(false)
        );
    }
}
