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
        addFieldsToMetadata(getMetadataSection(book), metadata);
        return metadata;
    }

    private String getMetadataSection(String book) {
        Matcher matcherStart = Pattern.compile("\\*\\*\\* START OF (THIS|THE) PROJECT GUTENBERG EBOOK .*?\\*\\*\\*", Pattern.DOTALL).matcher(book);
        return (matcherStart.find()) ? book.substring(0, matcherStart.start()) : book;
    }

    private void addFieldsToMetadata(String metadataSection, Map<String, String> metadata) {
        addTitle(metadataSection, metadata);
        addAuthor(metadataSection, metadata);
        addLanguage(metadataSection, metadata);
        addReleaseDate(metadataSection, metadata);
    }

    private void addReleaseDate(String metadataSection, Map<String, String> metadata) {
        metadata.put("releaseDate",  getMetadataFieldFromPattern(metadataSection, Pattern.compile("Release Date(.+[\r\n])+"))
                .replaceAll("\\[.*]","").trim());
    }

    private void addLanguage(String metadataSection, Map<String, String> metadata) {
        metadata.put("language",  getMetadataFieldFromPattern(metadataSection, Pattern.compile("Language(.+[\r\n])+")));
    }

    private void addAuthor(String metadataSection, Map<String, String> metadata) {
        metadata.put("author",  getMetadataFieldFromPattern(metadataSection, Pattern.compile("Author(.+[\r\n])+")));
    }

    private void addTitle(String metadataSection, Map<String, String> metadata) {
        metadata.put("title",  getMetadataFieldFromPattern(metadataSection, Pattern.compile("Title(.+[\r\n])+")));
    }

    private String getMetadataFieldFromPattern(String metadataSection, Pattern pattern) {
        Matcher matcher = pattern.matcher(metadataSection);
        return ((matcher.find()) ?
            metadataSection.substring(matcher.start(), matcher.end() - 1).split(":", 2)[1].trim() : "");
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
