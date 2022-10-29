package es.pentagono.crawler.readers;

import es.pentagono.crawler.BookReader;
import es.pentagono.crawler.Item;
import es.pentagono.crawler.parsers.GutenbergBookParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.IOException;

public class GutenbergBookReader implements BookReader {

    private final String GUTENBERG_URL = "https://www.gutenberg.org/cache/epub";
    private final GutenbergBookParser gutenbergBookParser;
    private final int maxBookSize = 10240000;

    public GutenbergBookReader() {
        this.gutenbergBookParser = new GutenbergBookParser();
    }

    @Override
    public Item read(int id) throws IOException {
        String url = GUTENBERG_URL+ "/" + id + "/pg" + id + ".txt";
        Document document = Jsoup.connect(url).maxBodySize(maxBookSize).get();
        return gutenbergBookParser.parse(url, getBookKeepingBreaklines(document));
    }

    private String getBookKeepingBreaklines(Document document) {
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        return Jsoup.clean(
            document.html().replaceAll("\\\\n", "\n"),
            "",
            Whitelist.none(),
            new Document.OutputSettings().prettyPrint(false)
        );
    }
}
