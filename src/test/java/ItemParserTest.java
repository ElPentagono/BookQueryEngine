import es.pentagono.crawler.ItemParser;
import es.pentagono.crawler.parsers.GutenbergBookParser;
import net.bytebuddy.implementation.bytecode.Multiplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class ItemParserTest {

    private final int SIZE = 1024;

    private final ItemParser itemParser;

    public ItemParserTest(ItemParser itemParser) {
        this.itemParser = itemParser;
    }

    @Test
    public void name() {
        InputStream is = getInputStream("bookExample.txt");
        BufferedReader reader = getBufferedReader(is);
        itemParser.parse(null, reader.lines().collect(Collectors.joining("\n")));
    }

    private static BufferedReader getBufferedReader(InputStream is) {
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(streamReader);
    }

    private static InputStream getInputStream(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(filename);
    }


    @Parameterized.Parameters
    public static Collection<ItemParser> implementations() {
        return List.of(
            new GutenbergBookParser()
        );
    }
}