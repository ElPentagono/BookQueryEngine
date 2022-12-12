package es.pentagono.parsers;

import es.pentagono.EventParser;
import es.pentagono.events.DownloadEvent;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GutenbergDownloadEventParser implements EventParser {

    @Override
    public DownloadEvent parse(String url, String book) {
        return new DownloadEvent(url,
                getMetadataFromBook(book),
                content(book),
                new Timestamp(System.currentTimeMillis()));
    }

    private Map<String, String> getMetadataFromBook(String book) {
        Map<String, String> metadata = new HashMap<>();
        addFieldsToMetadata(getPreContent(book), metadata);
        return metadata;
    }

    private String getPreContent(String book) {
        Matcher matcherStart = Pattern.compile("\\*\\*\\* START OF (THIS|THE) PROJECT GUTENBERG EBOOK .*?\\*\\*\\*", Pattern.DOTALL).matcher(book);
        return (matcherStart.find()) ? book.substring(0, matcherStart.start()) : book;
    }

    private void addFieldsToMetadata(String preContent, Map<String, String> metadata) {
        addTitle(preContent, metadata);
        addAuthor(preContent, metadata);
        addLanguage(preContent, metadata);
        addReleaseDate(preContent, metadata);
    }

    private void addReleaseDate(String preContent, Map<String, String> metadata) {
        metadata.put("releaseDate",  getMetadataFieldFromPattern(preContent, Pattern.compile("Release Date(.+[\r\n])+"))
                .replaceAll("\\[.*]","").trim());
    }

    private void addLanguage(String preContent, Map<String, String> metadata) {
        metadata.put("language",  getMetadataFieldFromPattern(preContent, Pattern.compile("Language(.+[\r\n])+")));
    }

    private void addAuthor(String preContent, Map<String, String> metadata) {
        metadata.put("author",  getMetadataFieldFromPattern(preContent, Pattern.compile("Author(.+[\r\n])+")));
    }

    private void addTitle(String preContent, Map<String, String> metadata) {
        metadata.put("title",  getMetadataFieldFromPattern(preContent, Pattern.compile("Title(.+[\r\n])+")));
    }

    private String getMetadataFieldFromPattern(String preContent, Pattern pattern) {
        Matcher matcher = pattern.matcher(preContent);
        return ((matcher.find()) ?
            preContent.substring(matcher.start(), matcher.end() - 1).split(":", 2)[1].trim() : "");
    }

    private String content(String book) {
        Matcher matcherStart = Pattern.compile("\\*\\*\\* START OF (THIS|THE) PROJECT GUTENBERG EBOOK .*?\\*\\*\\*", Pattern.DOTALL).matcher(book);
        Matcher matcherEnd = Pattern.compile("\\*\\*\\* END OF THE PROJECT GUTENBERG .*?(?=(\\*\\*\\*))", Pattern.DOTALL).matcher(book);
        return content(book, matcherStart, matcherEnd);
    }

    private String content(String book, Matcher matcherStart, Matcher matcherEnd) {
        return book.substring(
                ((!matcherStart.find()) ? 0 : matcherStart.end()),
                ((!matcherEnd.find()) ? book.length() : matcherEnd.start())
        ).trim();
    }
}
