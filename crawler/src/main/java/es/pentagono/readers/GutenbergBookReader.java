package es.pentagono.readers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import es.pentagono.BookReader;
import es.pentagono.EventParser;
import es.pentagono.events.DownloadEvent;
import es.pentagono.parsers.GutenbergDownloadEventParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class GutenbergBookReader implements BookReader {
    private static final EventParser DownloadEventParser = new GutenbergDownloadEventParser();

    @Override
    public DownloadEvent read(String url) {
        return (DownloadEvent) DownloadEventParser.parse(url, getBook(url));
    }

    private String getBook(String url) {
        try {
            Unirest.setTimeouts(0, 0);
            return Unirest.get(url).asString().getBody();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(String url) {
        try {
            HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
            int responseCode = huc.getResponseCode();
            return HttpURLConnection.HTTP_OK == responseCode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
