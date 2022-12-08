import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class MetadataParserTest {
    @Test
    public void testFirstBook() throws UnirestException {
        String book =  read("https://www.gutenberg.org/files/69274/69274-0.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("October 30, 2022");
    }

    @Test
    public void testSecondBook() throws UnirestException {
        String book =  read("https://www.gutenberg.org/cache/epub/12/pg12.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("February, 1991");
    }

    @Test
    public void testThirdTest() throws UnirestException {
        String book =  read("https://www.gutenberg.org/cache/epub/8/pg8.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("December, 1978");
    }

    @Test
    public void testFourthTest() throws UnirestException {
        String book =  read("https://www.gutenberg.org/cache/epub/13/pg13.txt");
        Matcher matcher = Pattern.compile("Release Date(.+[\r\n])+").matcher(book);
        matcher.find();
        assertThat(book.substring(matcher.start(), matcher.end())
                .replaceAll("\\[.*]","").split(":", 2)[1].trim())
                .isEqualTo("March 8, 1992");
    }

    public String read(String url) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        return Unirest.get(url).asString().getBody();
    }
}
