package es.pentagono.crawler.parsers;

import es.pentagono.crawler.DownloadEvent;
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
            content(book),
            new Timestamp(System.currentTimeMillis()));
    }

    private Map<String, String> getMetadataFromBook(String book) {
        Map<String, String> metadata = new HashMap<>();
        addFieldsToMetadata(book, metadata);
        return metadata;
    }

    private void addFieldsToMetadata(String book, Map<String, String> metadata) {
        addTitle(book, metadata);
        addAuthor(book, metadata);
        addLanguage(book, metadata);
        addReleaseDate(book, metadata);
    }

    private void addReleaseDate(String book, Map<String, String> metadata) {
        metadata.put("releaseDate",  getMetadataFieldFromPattern(book, Pattern.compile("Release Date.+?(?=(\\[.*]))", Pattern.DOTALL)));
    }

    private void addLanguage(String book, Map<String, String> metadata) {
        metadata.put("language",  getMetadataFieldFromPattern(book, Pattern.compile("Language(.+[\r\n])+")));
    }

    private void addAuthor(String book, Map<String, String> metadata) {
        metadata.put("author",  getMetadataFieldFromPattern(book, Pattern.compile("Author(.+[\r\n])+")));
    }

    private void addTitle(String book, Map<String, String> metadata) {
        metadata.put("title",  getMetadataFieldFromPattern(book, Pattern.compile("Title(.+[\r\n])+")));
    }

    private String getMetadataFieldFromPattern(String book, Pattern pattern) {
        Matcher matcher = pattern.matcher(book);
        return ((matcher.find()) ? book.substring(matcher.start(), matcher.end() - 1).split(":", 2)[1].trim()
            : null);
    }

    private String content(String book) {
        Matcher matcherStart = Pattern.compile("\\*\\*\\* START OF THE PROJECT GUTENBERG .*?(?=(\\*\\*\\*))", Pattern.DOTALL).matcher(book);
        Matcher matcherEnd = Pattern.compile("\\*\\*\\* END OF THE PROJECT GUTENBERG .*?(?=(\\*\\*\\*))", Pattern.DOTALL).matcher(book);
        return content(book, matcherStart, matcherEnd);
    }

    private String content(String book, Matcher matcherStart, Matcher matcherEnd) {
        return book.substring(
                ((!matcherStart.find()) ? -1 : matcherStart.end()),
                ((!matcherEnd.find()) ? book.length() : matcherEnd.start())
        ).trim();
    }
}
