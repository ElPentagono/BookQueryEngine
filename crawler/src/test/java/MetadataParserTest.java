import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class MetadataParserTest {
    @Test
    public void testFirstBook() throws IOException {
        String book =  read("https://www.gutenberg.org/files/69274/69274-0.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("October 30, 2022");
    }

    @Test
    public void testSecondBook() throws IOException {
        String book =  read("https://www.gutenberg.org/cache/epub/12/pg12.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("February, 1991");
    }

    @Test
    public void testThirdTest() throws IOException {
        String book =  read("https://www.gutenberg.org/cache/epub/8/pg8.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("December, 1978");
    }

    @Test
    public void testFourthTest() throws IOException {
        String book =  read("https://www.gutenberg.org/cache/epub/13/pg13.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("March 8, 1992");
    }

    public String read(String url) throws IOException {
        Document document = Jsoup.connect(url).maxBodySize(10240000).get();
        return getBookKeepingBreakLines(document);
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
