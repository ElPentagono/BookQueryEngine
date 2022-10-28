package es.pentagono.crawler.parsers;

import com.google.gson.Gson;
import es.pentagono.crawler.Item;
import es.pentagono.crawler.ItemParser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GutenbergBookParser implements ItemParser {

    @Override
    public Item parse(String url, String book) {
        return new Item(url,
            getMetadataFromBook(book),
            getContentFromBook(book)
        );
    }

    private String getMetadataFromBook(String book) {
        Map<String, String> metadata = new HashMap<>();
        for (String metadataField : new String[]{"Title", "Author", "Language"}) {
            metadata.put(metadataField.toLowerCase(),
                getMetadataFieldFromPattern(book, Pattern.compile(metadataField + "(.+[\n])+")));
        }
        metadata.put("releaseDate",
            getMetadataFieldFromPattern(book, Pattern.compile("Release Date.+?(?=(\\[.*]))", Pattern.DOTALL)));
        return new Gson().toJson(metadata);
    }

    private String getMetadataFieldFromPattern(String book, Pattern pattern) {
        Matcher matcher = pattern.matcher(book);
        return ((matcher.find()) ? book.substring(matcher.start(), matcher.end() - 1).split(":", 2)[1]
            : null);
    }

    private String getContentFromBook(String book) {
        Matcher matcherStart = getMatcherFromPattern("\\*\\*\\* START OF THE PROJECT GUTENBERG .* \\*\\*\\*", book);
        Matcher matcherEnd = getMatcherFromPattern("\\*\\*\\* END OF THE PROJECT GUTENBERG .* \\*\\*\\*", book);
        return book.substring(
            ((!matcherStart.find()) ? -1 : matcherStart.end()),
            ((!matcherEnd.find()) ? -1 : matcherEnd.start())
        );
    }

    private Matcher getMatcherFromPattern(String regex, String book) {
        return Pattern.compile(regex).matcher(book);
    }
}
