package es.pentagono.crawler;

import es.pentagono.Document;

public interface ItemParser {
    Item parse(String url, String book);
}
