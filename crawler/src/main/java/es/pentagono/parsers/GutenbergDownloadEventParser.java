package es.pentagono.parsers;

import es.pentagono.EventParser;
import es.pentagono.Metadata;
import es.pentagono.events.DownloadEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GutenbergDownloadEventParser implements EventParser {

    private final DateTimeFormatter onlyYearFormat = new DateTimeFormatterBuilder()
            .appendPattern("yyyy")
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter();

    @Override
    public DownloadEvent parse(String url, String book) {
        return new DownloadEvent(url,
                getMetadataFromBook(getMetadataSection(book)),
                content(book),
                System.currentTimeMillis());
    }

    private Metadata getMetadataFromBook(String metadataSection) {
        return new Metadata(
                getTitle(metadataSection),
                getAuthor(metadataSection),
                getLanguage(metadataSection),
                getReleaseDate(metadataSection)
        );
    }

    private String getMetadataSection(String book) {
        Matcher matcherStart = Pattern.compile("\\*\\*\\* START OF (THIS|THE) PROJECT GUTENBERG EBOOK .*?\\*\\*\\*", Pattern.DOTALL).matcher(book);
        return (matcherStart.find()) ? book.substring(0, matcherStart.start()) : book;
    }

    private LocalDate getReleaseDate(String metadataSection) {
        String releaseDate = getMetadataFieldFromPattern(metadataSection, Pattern.compile("Release Date(.+[\r\n])+")).replaceAll("\\[.*]","").trim();
        return getReleaseDateValue(releaseDate);
    }

    private String getLanguage(String metadataSection) {
        return getValue(getMetadataFieldFromPattern(metadataSection, Pattern.compile("Language(.+[\r\n])+")));
    }

    private String getAuthor(String metadataSection) {
        return getAuthorValue(getMetadataFieldFromPattern(metadataSection, Pattern.compile("Author(.+[\r\n])+")));
    }

    private String getTitle(String metadataSection) {
        return getValue(getMetadataFieldFromPattern(metadataSection, Pattern.compile("Title(.+[\r\n])+")));
    }

    private LocalDate getReleaseDateValue(String releaseDate) {
        return (releaseDate.equals("") || releaseDate.equals("Unknown")) ? null : LocalDate.parse(releaseDate.substring(releaseDate.length() - 4), onlyYearFormat);
    }

    private String getAuthorValue(String author) {
        return (author.equals("") || author.equals("Anonymous")) ? null : author;
    }

    private String getValue(String metadataField) {
        return metadataField.equals("") ? null : metadataField;
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
