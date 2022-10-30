package es.pentagono.crawler.parsers;

import es.pentagono.crawler.events.DownloadEvent;
import es.pentagono.crawler.EventParser;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GutenbergBookParser implements EventParser {

    @Override
    public DownloadEvent parse(String url, String book) {
        return new DownloadEvent(url,
            getMetadataFromBook(book),
            getContentFromBook(book),
            new Timestamp(System.currentTimeMillis()));
    }

    private Map<String, String> getMetadataFromBook(String book) {
        Map<String, String> metadata = new HashMap<>();
        for (String metadataField : new String[]{"Title", "Author", "Language"})
            metadata.put(metadataField.toLowerCase(),
                getMetadataFieldFromPattern(book, Pattern.compile(metadataField + "(.+[\r\n])+")));

        metadata.put("releaseDate",
            getMetadataFieldFromPattern(book, Pattern.compile("Release Date.+?(?=(\\[.*]))", Pattern.DOTALL)));
        return metadata;
    }

    private String getMetadataFieldFromPattern(String book, Pattern pattern) {
        Matcher matcher = pattern.matcher(book);
        return ((matcher.find()) ? book.substring(matcher.start(), matcher.end() - 1).split(":", 2)[1].trim()
            : null);
    }

    private String getContentFromBook(String book) {
        Matcher matcherStart = getMatcherFromPattern("\\*\\*\\* START OF THE PROJECT GUTENBERG .* \\*\\*\\*", book);
        Matcher matcherEnd = getMatcherFromPattern("\\*\\*\\* END OF THE PROJECT GUTENBERG .* \\*\\*\\*", book);
        return book.substring(
            ((!matcherStart.find()) ? -1 : matcherStart.end()),
            ((!matcherEnd.find()) ? book.length() : matcherEnd.start())
        ).trim();
    }

    private Matcher getMatcherFromPattern(String regex, String book) {
        return Pattern.compile(regex).matcher(book);
    }
}
