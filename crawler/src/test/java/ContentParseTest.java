import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentParseTest {
    @Test
    public void test8thBook() throws IOException {
        String book =  read("https://www.gutenberg.org/cache/epub/8/pg8.txt");
        assertThat(findStartContent(book)).isEqualTo(627);
    }

    @Test
    public void test1thBook() throws IOException {
        String book =  read("https://www.gutenberg.org/cache/epub/1/pg1.txt");
        assertThat(findStartContent(book)).isEqualTo(-1);
    }

    @Test
    public void test9thBook() throws IOException {
        String book =  read("https://www.gutenberg.org/cache/epub/9/pg9.txt");
        assertThat(findStartContent(book)).isEqualTo(624);
    }

    @Test
    public void test10thBook() throws IOException {
        String book =  read("https://www.gutenberg.org/cache/epub/10/pg10.txt");
        assertThat(findStartContent(book)).isEqualTo(712);
    }

    @Test
    public void testBookWithLongTitle() throws IOException {
        String book =  read("https://www.gutenberg.org/files/69274/69274-0.txt");
        assertThat(findStartContent(book)).isEqualTo(1013);
    }

    public int findStartContent(String book) {
        Matcher matcher = Pattern.compile("\\*\\*\\* START OF (THIS|THE) PROJECT GUTENBERG EBOOK .*?\\*\\*\\*", Pattern.DOTALL).matcher(book);
        if (matcher.find()) {
            System.out.println(book.substring(0, matcher.end()));
            System.out.println("The character was: " + matcher.end());
            return matcher.end();
        }
        return -1;
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
