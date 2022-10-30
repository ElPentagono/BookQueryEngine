package es.pentagono.crawler;

public interface EventParser {
    Event parse(String url, String book);
}
