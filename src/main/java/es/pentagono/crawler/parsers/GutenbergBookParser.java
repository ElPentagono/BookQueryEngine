package es.pentagono.crawler.parsers;

import es.pentagono.crawler.Item;
import es.pentagono.crawler.ItemParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GutenbergBookParser implements ItemParser {

    @Override
    public Item parse(String url, String book) {
        String content = getContentFromBook(book);
        String metadata = getMetadataFromBook(book);
        return null;
    }

    private String getMetadataFromBook(String book) {
        //String[] metadataFields = {"Title", "Author", "Language", "Release Date", "Updated Date"}; // TODO do it in loop
        Matcher matcher = Pattern.compile("Title(.+[\n])+").matcher(book);
        if (matcher.find()) {
            String cosa = book.substring(matcher.start(), matcher.end());
            String cosa2 = ((!matcher.find()) ? null
                : book.substring(matcher.start(), matcher.end()).split(":", 2)[1]);
        }

        String title = getMetadataFieldFromPattern(book, "Title.*[\n\r]");
        String author = getMetadataFieldFromPattern(book, "Author.*[\n\r].*[\n\r]");
        String language = getMetadataFieldFromPattern(book, "Language.*[\n\r].*[\n\r].*");
        String releaseDate = getMetadataFieldFromPattern(book, "Release Date.*[\n\r].*[\n\r]");
        String updatedDate = getMetadataFieldFromPattern(book, "Updated Date.*[\n\r].*[\n\r]");
        return null;
    }

    private String getMetadataFieldFromPattern(String book, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(book);
        String cosa = book.substring(matcher.start(), matcher.end());
        return ((!matcher.find()) ? null
            : book.substring(matcher.start(), matcher.end()).split(":", 2)[1]);
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
