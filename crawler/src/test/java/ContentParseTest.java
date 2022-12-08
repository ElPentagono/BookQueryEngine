import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentParseTest {
    @Test
    public void test8thBook() throws UnirestException {
        String book =  read("https://www.gutenberg.org/cache/epub/8/pg8.txt");
        assertThat(findStartContent(book)).isEqualTo(628);
    }

    @Test
    public void test1thBook() throws UnirestException {
        String book =  read("https://www.gutenberg.org/cache/epub/1/pg1.txt");
        assertThat(findStartContent(book)).isEqualTo(-1);
    }

    @Test
    public void test9thBook() throws UnirestException {
        String book =  read("https://www.gutenberg.org/cache/epub/9/pg9.txt");
        assertThat(findStartContent(book)).isEqualTo(625);
    }

    @Test
    public void test10thBook() throws UnirestException {
        String book =  read("https://www.gutenberg.org/cache/epub/10/pg10.txt");
        assertThat(findStartContent(book)).isEqualTo(713);
    }

    @Test
    public void testBookWithLongTitle() throws UnirestException {
        String book =  read("https://www.gutenberg.org/files/69274/69274-0.txt");
        assertThat(findStartContent(book)).isEqualTo(1014);
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

    private String read(String url) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        return Unirest.get(url).asString().getBody();
    }
}
