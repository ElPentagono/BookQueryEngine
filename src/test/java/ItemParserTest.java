import es.pentagono.crawler.Item;
import es.pentagono.crawler.ItemParser;
import es.pentagono.crawler.parsers.GutenbergBookParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ItemParserTest {

    private final ItemParser itemParser;

    public ItemParserTest(ItemParser itemParser) {
        this.itemParser = itemParser;
    }

    @Test
    public void name() {
        InputStream is = getInputStream("bookExample.txt");
        BufferedReader reader = getBufferedReader(is);
        Item result = itemParser.parse("123", reader.lines().collect(Collectors.joining("\n")));
        assertThat(result.content).isEqualTo(getExpectedContent());
        assertThat(result.metadata).isEqualTo(getExpectedMetadata());
        assertThat(result.source).isEqualTo("123");
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

    private String getExpectedMetadata() {
        return "{\"releaseDate\":\" October 26, 2022\",\"author\":\" Christian David Ginsburg\",\"language\":\" English\",\"title\":\" The Kabbalah\\n       its doctrines, development, and literature\\n       its doctrines, development, and literature\"}" +
            "";
    }

    private String getExpectedContent() {
        return "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "                              THE KABBALAH\n" +
            "\n" +
            "               Its Doctrines, Development, and Literature\n" +
            "\n" +
            "\n" +
            "                                   By\n" +
            "                      CHRISTIAN D. GINSBURG Ll.D.\n" +
            "\n" +
            "\n" +
            "                           Second Impression\n" +
            "\n" +
            "\n" +
            "                                 London\n" +
            "                    GEORGE ROUTLEDGE & SONS LIMITED\n" +
            "                 Broadway House: 68–74 Carter Lane E.C.\n" +
            "\n" +
            "                                  1920\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "                                   TO\n" +
            "\n" +
            "               PERCY M. DOVE, ESQ., F.I.A., F.S.S., &c.,\n" +
            "\n" +
            "                 I AFFECTIONATELY INSCRIBE THIS ESSAY,\n" +
            "\n" +
            "              AS AN EXPRESSION OF MY HIGH REGARD FOR HIM,\n" +
            "              BOTH AS A FRIEND AND A CHRISTIAN GENTLEMAN.\n" +
            "\n" +
            "\n" +
            "                         CHRISTIAN D. GINSBURG.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "THE KABBALAH.\n" +
            "\n" +
            "\n" +
            "I.\n" +
            "\n" +
            "\n" +
            "A system of religious philosophy, or more properly of theosophy, which\n" +
            "has not only exercised for hundreds of years an extraordinary influence\n" +
            "on the mental development of so shrewd a people as the Jews, but has\n" +
            "captivated the minds of some of the greatest thinkers of Christendom in\n" +
            "the sixteenth and seventeenth centuries, claims the greatest attention\n" +
            "of both the philosopher and the theologian. When it is added that among\n" +
            "its captives were Raymond Lully, the celebrated scholastic,\n" +
            "metaphysician and chemist (died 1315); John Reuchlin, the renowned\n" +
            "scholar and reviver of oriental literature in Europe (born 1455, died\n" +
            "1522); John Picus di Mirandola, the famous philosopher and classical\n" +
            "scholar (1463–1494); Cornelius Henry Agrippa, the distinguished\n" +
            "philosopher, divine and physician (1486–1535); John Baptist von\n" +
            "Helmont, a remarkable chemist and physician (1577–1644); as well as our\n" +
            "own countrymen Robert Fludd, the famous physician and philosopher\n" +
            "(1574–1637), and Dr. Henry More (1614–1687); and that these men, after\n" +
            "restlessly searching for a scientific system which should disclose to\n" +
            "them “the deepest depths” of the Divine nature, and show them the real\n" +
            "tie which binds all things together, found the cravings of their minds\n" +
            "satisfied by this theosophy, the claims of the Kabbalah on the\n" +
            "attention of students in literature and philosophy will readily be\n" +
            "admitted. The claims of the Kabbalah, however, are not restricted to\n" +
            "the literary man and the philosopher: the poet too will find in it\n" +
            "ample materials for the exercise of his lofty genius. How can it be\n" +
            "otherwise with a theosophy which, we are assured, was born of God in\n" +
            "Paradise, was nursed and reared by the choicest of the angelic hosts in\n" +
            "heaven, and only held converse with the holiest of man’s children upon\n" +
            "earth. Listen to the story of its birth, growth and maturity, as told\n" +
            "by its followers.\n" +
            "\n";
    }
}